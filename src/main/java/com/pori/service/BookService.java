package com.pori.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pori.dto.BookHisDto;
import com.pori.dto.BookOpenDto;
import com.pori.dto.BookPetmateDto;
import com.pori.dto.PetmateBookDto;
import com.pori.dto.PetmateFormDto;
import com.pori.dto.PetmateImgDto;
import com.pori.entity.Book;
import com.pori.entity.BookPetmate;
import com.pori.entity.Member;
import com.pori.entity.Petmate;
import com.pori.entity.PetmateImg;
import com.pori.repository.BookRepository;
import com.pori.repository.MemberRepository;
import com.pori.repository.PetmateImgRepository;
import com.pori.repository.PetmateRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

	private final PetmateRepository petmateRepository;
	private final MemberRepository memberRepository;
	private final PetmateImgRepository petmateImgRepository;
	private final BookRepository bookRepository;

	// 펫메 가져오기
	@Transactional(readOnly = true)
	public PetmateBookDto getPetmateBookDtl(Long petmateId) {
		// 1. petmate_img 테이블의 이미지를 가져온다
		List<PetmateImg> petmateImgList = petmateImgRepository.findByPetmateIdOrderByIdAsc(petmateId);

		// PetmateImg 엔티티 객체 -> PetmateImgDto로 변환
		List<PetmateImgDto> petmateImgDtoList = new ArrayList<>();

		for (PetmateImg petmateImg : petmateImgList) {
			PetmateImgDto petmateImgDto = PetmateImgDto.of(petmateImg);
			petmateImgDtoList.add(petmateImgDto);
		}

		// 2. petmate 테이블에 있는 데이터를 가져온다
		Petmate petmate = petmateRepository.findById(petmateId).orElseThrow(EntityNotFoundException::new);

		PetmateBookDto petmateBookDto = PetmateBookDto.of(petmate);
		// 3. PetmateFormDto에 이미지 정보(petmateImgDtoList)를 넣어준다
		petmateBookDto.setPetmateImgDtoList(petmateImgDtoList);

		return petmateBookDto;
	}

	// 예약 저장하기
	public Long saveBook(BookOpenDto bookOpenDto, String email) {

		// 예약할 펫메 조회
		Petmate petmate = petmateRepository.findById(bookOpenDto.getPetmateId())
				.orElseThrow(EntityNotFoundException::new);

		// 현재 로그인한 회원의 이메일을 이용해서 회원 정보 조회
		Member member = memberRepository.findByEmail(email);

		Book book = bookOpenDto.createBook(member, petmate);
		bookRepository.save(book);

		return book.getId();

	}

	
	 //예약 목록을 가져오는 서비스
	  @Transactional(readOnly = true) 
	  public Page<BookHisDto> getBookList(String email, Pageable pageable) {
	
		  //유저 아이디와 페이징 조건을 이용하여 예약 목록 조회
		  List<Book> books = bookRepository.findBooks(email, pageable);
		  
		  Long totalCount = bookRepository.countBook(email);
		  
		  List<BookHisDto> bookHisDtos = new ArrayList<>();
		  
		  for(Book book : books) {
		  BookHisDto bookHisDto = new BookHisDto(book);
		  List<BookPetmate> bookPetmates = book.getBookPetmates();
		  	
		  	for(BookPetmate bookPetmate : bookPetmates) {
		  		PetmateImg petmateImg = petmateImgRepository.findByPetmateIdAndRepimgYn(bookPetmate.getPetmate().getId(), "Y");
		  		BookPetmateDto bookPetmateDto = new BookPetmateDto(bookPetmate, petmateImg.getImgUrl());
		  		bookHisDto.addBookPetmateDto(bookPetmateDto);
		  	}
		  	bookHisDtos.add(bookHisDto);
		  }
		  return new PageImpl<BookHisDto>(bookHisDtos, pageable, totalCount);
	  }
}
