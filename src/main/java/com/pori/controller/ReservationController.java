package com.pori.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.pori.dto.ReservationDto;
import com.pori.dto.ReservationHistDto;
import com.pori.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;
	
	@PostMapping(value = "/reservation")
	public @ResponseBody ResponseEntity reservation(@RequestBody @Valid ReservationDto reservationDto,
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
		Long reservationId;
		
		try {
			reservationId = reservationService.reservation(reservationDto, email);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		
		
		return new ResponseEntity<Long>(reservationId, HttpStatus.OK);
	}
	
	//예약 내역
	@GetMapping(value = {"/reserved", "/reserved/{page}"})
	public String reserveHist(@PathVariable("page") Optional<Integer> page,
			Principal principal, Model model) {
		
		// 한 페이지당 4개의 데이터를 가지고 오도록 설정
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		
		// 서비스 호출
		String email = principal.getName();
		Page<ReservationHistDto> reservationHistDtoList = reservationService.getReservationList(email, pageable);
		
		// 서비스에서 가져온 값들을 view단에 모델을 이용해 전송
		model.addAttribute("reserves",reservationHistDtoList);
		model.addAttribute("maxPage", 5);
		
		
		return "reserve/reserveHist";
	}
	
	
	//주문 취소
	@PostMapping("/reserved/{reserveId}/cancel")
	public @ResponseBody ResponseEntity cancelReservation(@PathVariable("reserveId") Long reserveId, Principal principal) {
		// 주문취소 권한이 있는지 확인
		if(!reservationService.validateReservation(reserveId, principal.getName())) {
			return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
			
		}
		
		//주문 취소
		reservationService.canelReservation(reserveId);
		return new ResponseEntity<Long>(reserveId, HttpStatus.OK);
	}
	
	//주문 삭제
	@DeleteMapping("/reserved/{reserveId}/delete")
	public @ResponseBody ResponseEntity deleteReservation(@PathVariable("reserveId") Long reserveId, Principal principal) {
		
		if(!reservationService.validateReservation(reserveId, principal.getName())) {
			return new ResponseEntity<String>("주문 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
			
		}
		
		//주문 삭제
		reservationService.deleteReservation(reserveId);
		return new ResponseEntity<Long>(reserveId, HttpStatus.OK);
	}
	
	
	
}
