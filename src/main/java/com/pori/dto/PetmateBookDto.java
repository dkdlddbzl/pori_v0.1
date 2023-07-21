package com.pori.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.pori.constrant.PetmateStatus;
import com.pori.constrant.ReviewStatus;
import com.pori.entity.Petmate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetmateBookDto {
	
	
	private Long id;
	
	private String petmateNm;
	
	private int price;
	
	private String petmateDetail;
	
	private PetmateStatus petmateStatus;
	
	private int runCount;
	
	private int likeCount;
	
	private int followCount;
	
	private ReviewStatus reviewStatus;
	
	private Long memberId;
	
	//상품 이미지 정보를 저장
	private List<PetmateImgDto> petmateImgDtoList = new ArrayList<>();
	
	
	//상품 이미지 아이디들을 저장 -> 수정시에 이미지 아이디를 담아둘 용도
	private List<Long> petmateImgIds = new ArrayList<>();
	
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//dto -> 엔티티로 바꿔주는
	public Petmate createPetmate() {
		return modelMapper.map(this, Petmate.class);
	}
	
	//entity를 dto로 바꿔줌
	public static PetmateBookDto of(Petmate petmate) {
		return modelMapper.map(petmate, PetmateBookDto.class);
	}
	
	
}
