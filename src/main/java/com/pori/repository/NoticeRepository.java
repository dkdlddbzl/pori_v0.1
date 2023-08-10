package com.pori.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pori.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	
	Page<Notice> findAllByOrderByIdDesc(Pageable pageable);
}
