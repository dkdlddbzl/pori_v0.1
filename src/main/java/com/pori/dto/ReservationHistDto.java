package com.pori.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.pori.constrant.ReserveStatus;
import com.pori.entity.Reservation;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReservationHistDto {
	
	public ReservationHistDto(Reservation reservation) {
		this.reserveId = reservation.getId();
		this.reserveDate = reservation.getReserveDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		this.status = reservation.getStatus();
		
	}
	
	
	private Long reserveId;
	
	private String reserveDate;
	
	private ReserveStatus status;
	
	private List<ReservationPetmateDto> reservationPetmateDtoList = new ArrayList<>();
	
	public void addReservationPetmateDto(ReservationPetmateDto reservationPetmateDto) {
		this.reservationPetmateDtoList.add(reservationPetmateDto);
	}
}
