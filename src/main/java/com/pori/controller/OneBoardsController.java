package com.pori.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pori.dto.FaqFormDto;
import com.pori.dto.OneBoardsFormDto;
import com.pori.entity.Faq;
import com.pori.entity.OneBoards;
import com.pori.repository.OneBoardsRepository;
import com.pori.service.ChattService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Transactional
public class OneBoardsController {
	private final ChattService chattService;
	private final OneBoardsRepository oneBoardsRepository;
	
	//one 생성페이지
	@GetMapping(value = "/members/chat")
	public String oneForm(Model model) {
		model.addAttribute("oneBoardsDto", new OneBoardsFormDto());
		
		return "member/createChatt";
	}
	
	//one 인서트
	@PostMapping(value = "/members/chat")
	public String oneNew(@Valid OneBoardsFormDto oneBoardsFormDto, BindingResult bindingResult,
			Model model, Principal principal, @Valid OneBoards oneBoards) {
		
		if(bindingResult.hasErrors()) {
			return "member/createChatt";
		}
		String email = principal.getName();
		
		try {
		chattService.saveOneBoards(oneBoardsFormDto, email, oneBoards);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품등록 중 에러가 발생했습니다.");
			return "member/createChatt";
		}
		return "redirect:/members/chatid";
	}
	
	//one id페이지보기	
	@GetMapping(value = "/members/chatid")
	public String oneDtl(Model model, Principal principal, @PathVariable("page") Optional<Integer> page) {
		String email = principal.getName();
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 1);
		Page<OneBoards> oneBoards = chattService.getOneList(email, pageable);
		model.addAttribute("oneBoards", oneBoards);
		
		return "member/chatId";
	}	
		
	//one id넘기기
	@GetMapping(value = "/members/chatid/{roomId}")
	public String oneDtl(Model model, @PathVariable("roomId") Long roomId) {
		
		OneBoards oneBoards = oneBoardsRepository.findById(roomId).orElseThrow(EntityNotFoundException::new);
		
		model.addAttribute("oneBoards",oneBoards);
		
		return "member/chatRoom";
	}	
	
	
	
}
	

