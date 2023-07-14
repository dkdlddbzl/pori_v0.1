package com.pori.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainPetmateDto {
	private Long id;
	
	private String petmateNm;
	
	private String petmateDetail;
	
	private String imgUrl;
	
	private Integer price;
	
	private Integer runCount;
	
	private Integer likeCount;
	
	@QueryProjection //쿼리 dsl로 결과 조회 할때 MainItemDto 객체로 바로 받아올 수 있다.
	public MainPetmateDto(Long id, String petmateNm, String petmateDetail, String imgUrl, Integer price, Integer runCount, Integer likeCount) {
		this.id = id;
		this.petmateNm = petmateNm;
		this.petmateDetail = petmateDetail;
		this.imgUrl = imgUrl;
		this.price = price;
		this.runCount = runCount;
		this.likeCount = likeCount;
	}
}
