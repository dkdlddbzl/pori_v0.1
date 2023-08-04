package com.pori.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.pori.constrant.Role;
import com.pori.dto.MemberFormDto;
import com.pori.dto.MemberPageDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity //엔티티 클래스로 정의
@Table(name="member") //테이블 이름 지정
@Getter
@Setter
@ToString
public class Member extends BaseEntity{
	
	@Id
	@Column(name="member_id") //테이블로 생설될때 컬럼이름을 지정해준다
	@GeneratedValue(strategy = GenerationType.AUTO) // 기본키를 자동으로 생성
	private Long id;
	
	@Column(unique = true) //중복된 값이 들어올 수 없다
	private String email;
	
	private String name;
	
	private String password;
	
	private String address;
	
	private int rating_score;
	
	private int run_count;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		
		member.setEmail(memberFormDto.getEmail());
		member.setName(memberFormDto.getName());
		member.setAddress(memberFormDto.getAddress());
		
		//패스워드 암호화
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		
		//MemberFormDto를 -> Member 엔티티 객체로 변환
		member.setPassword(password);
		
		member.setRole(Role.ADMIN); //관리자로 가입할때
		//member.setRole(Role.USER); //일반 사용자로 가입할떄
		
		return member;
	}
	
	//member entity 수정
	public void updateMember(MemberPageDto memberPageDto) {
		this.name = memberPageDto.getName();
		this.address = memberPageDto.getAddress();
		this.rating_score = memberPageDto.getRating_score();
		this.run_count = memberPageDto.getRun_count();
	}
	
	
	
}
