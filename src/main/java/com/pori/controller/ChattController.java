package com.pori.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pori.dto.OneBoardsFormDto;
import com.pori.entity.OneBoards;
import com.pori.repository.FaqRepository;
import com.pori.repository.OneBoardsRepository;
import com.pori.service.ChattService;
import com.pori.service.FaqService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChattController {


	
	
	@RequestMapping("/chat")
	public ModelAndView chatt(OneBoards oneBoards) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("member/chatRoom");
		oneBoards.closeRoom();
		return mv;
	}
	
	
	
	
	
}
