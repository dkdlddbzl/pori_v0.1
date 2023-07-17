package com.pori.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pori.constrant.PetmateStatus;
import com.pori.constrant.ReserveStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="reservation")
@Getter
@Setter
@ToString
public class Reservation extends BaseEntity {

	@Id
	@Column(name="reserve_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="petmate_id")
	private Petmate petmate;
	
	private ReserveStatus status;
	
	private LocalDateTime reserveDate;
	
	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ReservationPetmate> reservationPetmates = new ArrayList<>();
	
	
	public void addReservationPetmate(ReservationPetmate reservationPetmate) {
		this.reservationPetmates.add(reservationPetmate);
		reservationPetmate.setReservation(this);
	}
	
	public static Reservation createReservation(Member member, List<ReservationPetmate> reservationPetmateList) {
		Reservation reservation = new Reservation();
		reservation.setMember(member);
		
		for(ReservationPetmate reservationPetmate : reservationPetmateList) {
			reservation.addReservationPetmate(reservationPetmate);
		}
		
		reservation.setStatus(ReserveStatus.ORDER);
		reservation.setReserveDate(LocalDateTime.now());
		
		return reservation;
	}
	
	public int getTotalPrice() {
		int totalPrice = 0;
		for
	}
	
	
	
	
	
}
