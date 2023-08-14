package com.pori.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqSearchDto {
	private String searchBy;
	private String searchQuery = "";
}
