package com.pori.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pori.entity.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long> {
	
	Page<Faq> findAllByOrderByIdDesc(Pageable pageable);
	
	Page<Faq> findByFaqRoleOrderByIdDesc(String FaqRole, Pageable pageable);
	
	
	
}
