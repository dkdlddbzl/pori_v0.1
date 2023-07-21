package com.pori.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.pori.dto.ReservationDto;
import com.pori.dto.ReservationHistDto;
import com.pori.dto.ReservationPetmateDto;
import com.pori.entity.Member;
import com.pori.entity.Petmate;
import com.pori.entity.PetmateImg;
import com.pori.entity.Reservation;
import com.pori.entity.ReservationPetmate;
import com.pori.repository.MemberRepository;
import com.pori.repository.PetmateImgRepository;
import com.pori.repository.PetmateRepository;
import com.pori.repository.ReservationRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {
	private final PetmateRepository petmateRepository;
	private final MemberRepository memberRepository;
	private final ReservationRepository reservationRepository;
	private final PetmateImgRepository petmateImgRepository;
	
	public Long reservation(ReservationDto reservationDto, String email) {
		
		//예약할 펫메 조회
		Petmate petmate = petmateRepository.findById(reservationDto.getPetmateId()).orElseThrow(EntityNotFoundException::new);
		
		//현재 로그인한 회원의 이메일을 이용해서 회원 정보 조회
		Member member = memberRepository.findByEmail(email);
		
		// 주문할 예약 엔티티와 예약 수량을 이용하여 예약펫메 엔티티 생성
		List<ReservationPetmate> reservationPetmateList = new ArrayList<>();
		ReservationPetmate reservationPetmate = ReservationPetmate.createReservePermate(petmate, reservationDto.getCount());
		reservationPetmateList.add(reservationPetmate);
		
		//회원 정보와 예약할 상품 리스트 정보를 이용하여 예약 엔티티를 생성
		Reservation reservation = Reservation.createReservation(member, reservationPetmateList);
		reservationRepository.save(reservation);
		
		
		return reservation.getId();
	}
	
	
	//예약 목록 가져오는 서비스
	@Transactional(readOnly = true)
	public Page<ReservationHistDto> getReservationList(String email, Pageable pageable){
		
		//유저 아이디와 페이징 조건을 이용하여 예약 목록 조회
		List<Reservation> reservations = reservationRepository.findReservations(email, pageable);
		
		Long totalCount = reservationRepository.countReservation(email);
		
		List<ReservationHistDto> reservationHistDtos = new ArrayList<>();
		
		// 유저의 예약 총 개수를 구한다
		for(Reservation reservation : reservations) {
			ReservationHistDto reservationHistDto = new ReservationHistDto(reservation);
			List<ReservationPetmate> reservationPetmates = reservation.getReservationPetmates();
		
			// 예약 리스트를 순회하면서 예약 이력 페이지에 전달할 DTO 생성
			for(ReservationPetmate reservationPetmate : reservationPetmates) {
				PetmateImg petmateImg = petmateImgRepository.findByPetmateIdAndRepimgYn(reservationPetmate.getPetmate().getId(), "Y");
				ReservationPetmateDto reservationPetmateDto = new ReservationPetmateDto(reservationPetmate, petmateImg.getImgUrl());
				reservationHistDto.addReservationPetmateDto(reservationPetmateDto);
				
			}
			
			reservationHistDtos.add(reservationHistDto);
			
		}
		
		
		//페이지 구현 객체를 생성하여 return
		return new PageImpl<ReservationHistDto>(reservationHistDtos, pageable, totalCount);
	}
	
	//본인확인(현재 로그인한 사용자와 주문데이터를 생성한 사용자가 같은지 검사)
	@Transactional(readOnly = true)
	public boolean validateReservation(Long reserveId, String email) {
		Member curMember = memberRepository.findByEmail(email);
		Reservation reservation = reservationRepository.findById(reserveId).orElseThrow(EntityNotFoundException::new);
		
		Member savedMember = reservation.getMember(); //주문한 사용자 찾기
		
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
			
			return false;
		}
		
			return true;
		
	}
	
	//주문 취소
	public void canelReservation(Long reserveId) {
		Reservation reservation = reservationRepository.findById(reserveId).orElseThrow(EntityNotFoundException::new);
		
		reservation.cancelReservation();
	}
	
	
	//주문 삭제
	public void deleteReservation(Long reserveId) {
		Reservation reservation = reservationRepository.findById(reserveId).orElseThrow(EntityNotFoundException::new);
		
		reservationRepository.delete(reservation);
	}
	
	
	
	
}
