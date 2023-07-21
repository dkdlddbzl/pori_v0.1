package com.pori.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pori.dto.ReviewFormDto;
import com.pori.service.PetmateService;
import com.pori.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;
	private final PetmateService petmateService;
	
	@PostMapping(value = "/review")
	public @ResponseBody ResponseEntity review(@RequestBody @Valid ReviewFormDto reviewFormDto,
			BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
			
			for(FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage());
			}
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
		String email = principal.getName();
		Long reviewId;
		try {
			reviewId = reviewService.saveReview(reviewFormDto);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Long>(reviewId, HttpStatus.OK);
	}
	
	
	//리뷰삭제
	@DeleteMapping("/deleteReview")
	public @ResponseBody ResponseEntity deleteReview(@PathVariable("reviewId") Long reviewId, Principal principal) {
		
		reviewService.deleteReview(reviewId);
		return new ResponseEntity<Long>(reviewId, HttpStatus.OK);
		
		
	}
	
	
	
}
