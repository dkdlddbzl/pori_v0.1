package com.pori.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ChattController {

	@RequestMapping("/members/rr")
	public ModelAndView chatt() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("member/memberRr");
		return mv;
	}
}
