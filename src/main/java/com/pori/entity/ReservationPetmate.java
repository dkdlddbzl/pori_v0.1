package com.pori.entity;


import com.pori.constrant.PetmateStatus;
import com.pori.constrant.ReserveStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="reserve_petmate")
@Getter
@Setter
@ToString
public class ReservationPetmate {
	
	@Id
	@Column(name="reserve_petmate_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="petmate_id")
	private Petmate petmate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="reserve_id")
	private Reservation reservation;
	
	@Enumerated(EnumType.STRING)
	private ReserveStatus status;
	
	private int reservePrice;
	
	private String reserveDate;
	
	private int orderPrice;
	
	private int count;
	
	
	//주문할 상품하고 주문 수량을 통해서 orderItem 객체를만늗ㅁ
	public static ReservationPetmate createReservePermate(Petmate petmate, int count) {
		ReservationPetmate reservationPetmate = new ReservationPetmate();
		reservationPetmate.setPetmate(petmate);
		reservationPetmate.setCount(count);
		reservationPetmate.setOrderPrice(petmate.getPrice());
		reservationPetmate.setStatus(ReserveStatus.ORDER);
		
		
		return reservationPetmate;
	}
	
	public int getTotalPrice() {
		return orderPrice * count;
	}
	
	
	
	
	
}
