package com.pori.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pori.dto.MemberFormDto;
import com.pori.dto.MemberPageDto;
import com.pori.entity.Member;
import com.pori.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional //쿼리문 수행시 에러가 발생하면 변경된 데이터를 이전상태로 콜백 시켜줌
@RequiredArgsConstructor //@Autowired를 사용하지 않고 필드의 의존성 주입을 시켜준다
public class MemberService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	
	//회원가입 데이터를 DB에 저장한다.
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		Member savedMember = memberRepository.save(member);
		return savedMember; //회원가입된 데이터를 리턴
	}
	
	//이메일 중복 체크
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByEmail(member.getEmail());
		
		
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//사용자가 입력한 email이 DB에 있는지 쿼리문을 사용
		Member member = memberRepository.findByEmail(email); 
		
		if(member == null) {
			throw new UsernameNotFoundException(email);
		}
		
		//사용자가 있다면 userDetails 객체를 만들어서 반환
		return User.builder().username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}
	
	
	//회원 정보 가져오기
	public MemberPageDto getMemberPage(String email) {
		Member member = memberRepository.findByEmail(email);
		MemberPageDto memberPageDto = MemberPageDto.of(member);
		
		return memberPageDto;
	}
	
	
	
}
