package com.pori.dto;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.pori.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberPageDto {
	private Long id;
	
	private String email;
	
	private String name;
	
	private String address;
	
	private int rating_score;
	
	private int run_count;
	
	
	//상품 이미지 아이디들을 저장 -> 수정시에 이미지 아이디를 담아둘 용도.
	private List<Long> memberImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//dto -> entity로 바꿔줌
	public Member createMember() {
		return modelMapper.map(this, Member.class);
	}
	
	
	//entity를 dto로 바꿔줌
	public static MemberPageDto of(Member member) {
		return modelMapper.map(member, MemberPageDto.class);
	}
	
}
