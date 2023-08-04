package com.pori.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pori.dto.MemberFormDto;
import com.pori.dto.MemberPageDto;
import com.pori.entity.Member;
import com.pori.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	//회원가입화면
	@GetMapping(value = "/members/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	//로그인화면
	@GetMapping(value = "/members/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}
	
	// 마이페이지 화면
	@GetMapping(value = "/members/mypage")
	public String memberpage(Principal principal, Model model) {
		
		//1. 서비스 호출
		String email = principal.getName();
		MemberPageDto memberPageDtoList = memberService.getMemberPage(email);
		model.addAttribute("members", memberPageDtoList);
		
		
		return "member/memberPage";
	}
	// 마이페이지 수정화면 보기
	@GetMapping(value = "/members/mypageupdate")
	public String memberUpdate(Model model, Principal principal) {
		
		try {
			String email = principal.getName();
			MemberPageDto memberPageDto = memberService.getMemberPage(email);
			model.addAttribute("memberPageDto", memberPageDto);
			return "member/memberPageUpdate";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "member/memberPageUpdate";
	}
	
	
	//회원 가입
	@PostMapping(value = "/members/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		//@Valid : 유효성을 검증하려는 객체 앞에 붙인다
		//BindingResult : 유효성 검증 후의 결과가 들어있다.
		
		if(bindingResult.hasErrors()) {
			//에러가 있다면 회원가입 페이지로 이동
			return "member/memberForm";
		}
		
		try {
			//MemberFormDto -> Member Entity, 비밀번호 암호화
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
			
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		
		
		return "redirect:/";
	}
	
	//로그인 실패했을때
	@GetMapping(value="/members/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해 주세요.");
		return "member/memberLoginForm";
	}
	
	
	//맵화면
	@GetMapping(value = "/members/map")
	public String mapMember() {
		return "member/memberMap";
	}
	

	
	
	
}
