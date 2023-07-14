package com.pori.dto;

import org.modelmapper.ModelMapper;

import com.pori.entity.PetmateImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetmateImgDto {
	
	private Long id;
	
	private String imnName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//entity 를 Dto로 변환
		public static PetmateImgDto of(PetmateImg petmateImg) {
			return modelMapper.map(petmateImg, PetmateImgDto.class);
		}
	
}
