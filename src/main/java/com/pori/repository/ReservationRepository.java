package com.pori.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pori.entity.Reservation;


public interface ReservationRepository  extends JpaRepository<Reservation, Long>{

	
	//현재 로그인한 사용자의 주문데이터를 페이징 조건에 맞춰서 조회
	@Query("select i from Reservation i where i.member.email = :email order by i.reserveDate desc")
	List<Reservation> findReservations(@Param("email") String email, Pageable pageable);
	
	//현재 로그인한 회원의 주문 개수가 몇개인지 조회
	@Query("select count(i) from Reservation i where i.member.email = :email")
	Long countReservation(@Param("email") String email);
	
	
	
}
