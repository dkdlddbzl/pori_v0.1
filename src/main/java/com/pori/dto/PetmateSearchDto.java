package com.pori.dto;



import com.pori.constrant.PetmateStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetmateSearchDto {
	private String searchDateType;
	private PetmateStatus searchPetmateStatus;
	private String searchBy;
	private String searchQuery = "";
}
