package com.pori.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pori.entity.Petmate;


public interface PetmateRepository extends JpaRepository<Petmate, Long>, PetmateRepositoryCustom {
	
	//select * from petmate where petmate_nm = ?
	List<Petmate> findByPetmateNm(String PetmateNm);
	
	
	
	
	
	
	
	
}
