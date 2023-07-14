package com.pori.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pori.dto.MainPetmateDto;
import com.pori.dto.PetmateFormDto;
import com.pori.dto.PetmateSearchDto;
import com.pori.service.PetmateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PetmateController {
	
	private final PetmateService petmateService;
	
	//펫메 전체 리스트
	@GetMapping(value= "/petmate/list")
	public String petmateList(Model model, PetmateSearchDto petmateSearchDto,
			Optional<Integer> page) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 4);
		Page<MainPetmateDto> petmates = petmateService.getMainPetmatePage(petmateSearchDto, pageable);
		
		model.addAttribute("petmates", petmates);
		model.addAttribute("petmateSearchDto",petmateSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "petmate/petmateList";
	}
	
	
	
	
	
	// 펫메 등록 페이지
	@GetMapping(value = "/admin/petmate/new")
	public String petmateForm(Model model) {
		model.addAttribute("petmateFormDto", new PetmateFormDto());
		return "petmate/petmateForm";
	}
	
	//펫메 등록 (insert)
	@PostMapping(value = "/admin/petmate/new")
	public String petmateNew(@Valid PetmateFormDto petmateFormDto, BindingResult bindingResult, Model model,
			@RequestParam("petmateImgFile") List<MultipartFile> petmateImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "petmate/petmateForm";
		}
		
		//상품등록 전에 첫번째 이미지가 있는지 확인(첫번째 이미지 필수)
		if(petmateImgFileList.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "첫번째 프로필사진은 필수입니다.");
			return "petmate/petmateForm";
		}
		
		
		try {
			petmateService.savePetmate(petmateFormDto, petmateImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품등록 중 에러가 발생했습니다.");
			return "petmate/petmateForm";
		}
		
		return "redirect:/";
	}
}
