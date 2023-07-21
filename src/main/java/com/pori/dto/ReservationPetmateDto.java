package com.pori.dto;

import com.pori.entity.ReservationPetmate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationPetmateDto {

	
	//엔티티 -> Dto
	public ReservationPetmateDto(ReservationPetmate reservationPetmate,
			String imgUrl) {
		this.petmateNm = reservationPetmate.getPetmate().getPetmateNm();
		this.count = reservationPetmate.getCount();
		this.orderPrice = reservationPetmate.getOrderPrice();
		this.imgUrl = imgUrl;
	}
	
	
	private String petmateNm;
	
	private int count;
	
	private int orderPrice;
	
	private String imgUrl;
}
