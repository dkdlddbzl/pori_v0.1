package com.pori.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pori.dto.OneBoardsFormDto;
import com.pori.entity.Member;
import com.pori.entity.OneBoards;
import com.pori.repository.MemberRepository;
import com.pori.repository.OneBoardsRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChattService {
	private final MemberRepository memberRepository;
	private final OneBoardsRepository oneBoardsRepository;
	
	
	public Long saveOneBoards(OneBoardsFormDto oneBoardsFormDto, String email, OneBoards oneBoards) {
		Member member = memberRepository.findByEmail(email);
		
		
		OneBoards oneBoard = oneBoardsFormDto.createOneBoards(member, oneBoards);
		oneBoardsRepository.save(oneBoard);
		
		return oneBoard.getId();
	}
	
	public OneBoardsFormDto getBoard(Long roomId) {
		OneBoards oneBoards = oneBoardsRepository.findById(roomId).orElseThrow(EntityNotFoundException::new);
		
		OneBoardsFormDto oneBoardsFormDto = OneBoardsFormDto.of(oneBoards);
	
		return oneBoardsFormDto;
	}
	
	//one 목록 가져오기
	@Transactional(readOnly = true)
	public Page<OneBoards> getOneList(String email, Pageable pageable){
		
		
		Page<OneBoards> oneBoards = oneBoardsRepository.findOne(email, pageable);
		
		return oneBoards;
		
		
		
	}
}
