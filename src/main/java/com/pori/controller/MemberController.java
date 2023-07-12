package com.pori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	//회원가입화면
	@GetMapping(value = "/members/new")
	public String memberForm() {
		return "member/memberForm";
	}
}
