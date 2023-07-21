package com.pori.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pori.entity.Petmate;
import com.pori.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	List<Review> findByPetmateIdOrderByIdAsc(Long PetmateId);
	
	
}
