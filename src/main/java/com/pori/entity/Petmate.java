package com.pori.entity;


import com.pori.constrant.PetmateStatus;
import com.pori.dto.PetmateFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="petmate")
@Getter
@Setter
@ToString
public class Petmate extends BaseEntity {
	
	@Id
	@Column(name="petmate_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String petmateNm;
	
	@Column(nullable = false)
	private int price;
	
	@Lob
	@Column(nullable = false, columnDefinition = "longtext")
	private String petmateDetail;
	
	@Enumerated(EnumType.STRING)
	private PetmateStatus petmateStatus;
	
	private int runCount;
	
	private int likeCount;
	
	private int followCount;
	
	
	//petmate entity 수정
	
	public void updatePetmate(PetmateFormDto petmateFormDto) {
		this.petmateNm = petmateFormDto.getPetmateNm();
		this.price = petmateFormDto.getPrice();
		this.petmateDetail = petmateFormDto.getPetmateDetail();
		this.petmateStatus = petmateFormDto.getPetmateStatus();
		this.runCount = petmateFormDto.getRunCount();
		this.followCount = petmateFormDto.getFollowCount();
		this.likeCount = petmateFormDto.getLikeCount();
	}
	
	
	
	
	
	
	
}
