package com.pori.dto;

import com.pori.entity.BookPetmate;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookPetmateDto {
	
	
	public BookPetmateDto(BookPetmate bookPetmate, String imgUrl) {
		this.petmateNm = bookPetmate.getPetmate().getPetmateNm();
		this.imgUrl = imgUrl;
	}
	
	
	private String petmateNm;
	
	private String imgUrl;
	
	
	
	
}
