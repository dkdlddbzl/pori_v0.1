package com.pori.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pori.entity.PetmateImg;


public interface PetmateImgRepository extends JpaRepository<PetmateImg, Long>{
	
	List<PetmateImg> findByPetmateIdOrderByIdAsc(Long PetmateId);
	
	PetmateImg findByPetmateIdAndRepimgYn(Long petmateId, String repimgYn);
}
