package com.pori.dto;

import org.modelmapper.ModelMapper;

import com.pori.constrant.RoomStatus;
import com.pori.entity.Member;
import com.pori.entity.OneBoards;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OneBoardsFormDto {
	
	private Long id;
	
	private Long memberId;
	
	private RoomStatus roomStatus;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//dto -> entity로 바꿔줌
	public OneBoards createOneBoards(Member member, OneBoards oneboards) {
		this.memberId = member.getId();
		this.roomStatus = roomStatus.OPEN;
		return modelMapper.map(this, OneBoards.class);
	}
	
	
	//entity를 dto로 바꿔줌
	public static OneBoardsFormDto of(OneBoards oneBoards) {
		return modelMapper.map(oneBoards, OneBoardsFormDto.class);
	}
}
