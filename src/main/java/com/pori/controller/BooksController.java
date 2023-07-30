package com.pori.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.pori.dto.BookHisDto;
import com.pori.dto.BookOpenDto;
import com.pori.dto.MainPetmateDto;
import com.pori.dto.PetmateBookDto;
import com.pori.dto.PetmateSearchDto;
import com.pori.service.BookService;
import com.pori.service.MemberService;
import com.pori.service.PetmateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BooksController {
		
	private final BookService bookService;
	private final PetmateService petmateService;
	
	//예약오픈 화면
	@GetMapping(value = "/admin/books/open/{petmateId}")
	public String booksOpen(Model model, @PathVariable("petmateId") Long petmateId) {
		PetmateBookDto petmateBookDto = bookService.getPetmateBookDtl(petmateId);
		
		model.addAttribute("petmate", petmateBookDto);
		
		return "books/booksOpen";
	}
	
	
	
	//예약 생성
	@PostMapping(value = "/createBook")
	public @ResponseBody ResponseEntity book(@RequestBody @Valid BookOpenDto bookOpenDto,
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
		Long bookId;
		
		
		try {
			bookId = bookService.saveBook(bookOpenDto, email);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		
		return new ResponseEntity<Long>(bookId, HttpStatus.OK);
		
	}
	
	//예약 내역
	@GetMapping(value = {"/books/list", "/books/list/{page}"})
	public String bookHist(@PathVariable("page") Optional<Integer> page,
			Principal principal, Model model) {
		
		// 한페이지당 가져오는 데이터 설정
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		
		// 서비스 호출
		String email = principal.getName();
		Page<BookHisDto> bookHistDtoList = bookService.getBookList(email, pageable);
		
		
		//서비스 에서 가져온 값들을 view 단에 모델을 이용해 전송
		model.addAttribute("books",bookHistDtoList);
		model.addAttribute("maxPage", 5);
		
		return "books/booksList";
		
	}
	
	
	//예약삭제
	@DeleteMapping("/books/list/{bookId}/delete")
	public @ResponseBody ResponseEntity deleteBook(@PathVariable("bookId") Long bookId 
			, Principal principal) {
		
		if(!bookService.validateOrder(bookId, principal.getName())) {
			return new ResponseEntity<String>("예약 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
		bookService.deleteBook(bookId);
		
		return new ResponseEntity<Long>(bookId, HttpStatus.OK);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
