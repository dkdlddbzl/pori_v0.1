package com.pori.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.pori.entity.Petmate;
import com.pori.entity.Review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewFormDto {
	
	
	private Long id;
	
	private String reviewDtl;
	
	private Long petmateId;
	
	private String createdBy;
	
	private String updateTime;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	
	public Review createReview(Petmate petmate) {
		return modelMapper.map(this, Review.class);
	}
	
	public static ReviewFormDto of(Review review) {
		
		return modelMapper.map(review, ReviewFormDto.class);
	}
	
	
}
