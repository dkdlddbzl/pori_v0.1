package com.pori.dto;



import java.util.ArrayList;
import java.util.List;

import com.pori.constrant.BookStatus;
import com.pori.entity.Book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookHisDto {
	
	public BookHisDto(Book book) {
		this.bookId = book.getId();
		this.petmateId = book.getPetmate().getId();
		this.memberId = book.getMember().getId();
		this.bookStatus = book.getBookStatus();
		this.bookOpen = book.getBooksOpen();
	}
	
	private Long bookId;
	
	private Long petmateId;
	
	private Long memberId;
	
	private BookStatus bookStatus;
	
	private String bookOpen;
	
	private List<BookPetmateDto> bookPetmateDtoList = new ArrayList<>();
	
	
	public void addBookPetmateDto(BookPetmateDto bookPetmateDto) {
		this.bookPetmateDtoList.add(bookPetmateDto);
	}
	
}
