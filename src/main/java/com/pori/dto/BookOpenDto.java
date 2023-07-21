package com.pori.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.pori.constrant.BookStatus;
import com.pori.entity.Book;
import com.pori.entity.Member;
import com.pori.entity.Petmate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookOpenDto {
	
	
	
	
	private Long id;
	
	private Long petmateId;
	
	private Long memberId;
	
	private BookStatus bookStatus;
	
	private String booksOpen;
	
	
	
	//펫메 이미지 정보를 저장
	private List<PetmateImgDto> petmateImgDtoList = new ArrayList<>();
	
	//펫메 이미지 아이디들 저장
	private List<Long> petmateImgIds = new ArrayList<>();
	
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	
	// dto -> 엔티티로 바꿔주는
	public Book createBook(Member member ,Petmate petmate) {
		return modelMapper.map(this, Book.class);
	}
	
	//엔티티 -> dto
	public static BookOpenDto of(Book book) {
		return modelMapper.map(book, BookOpenDto.class);
	}
	
	
	
	
	
}
