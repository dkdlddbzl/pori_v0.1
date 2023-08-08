package com.pori.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pori.entity.OneBoards;

public interface OneBoardsRepository extends JpaRepository<OneBoards, Long> {
	
	@Query("select i from OneBoards i where i.member.email = :email order by i.Id desc")
	Page<OneBoards> findOne(@Param("email") String email, Pageable pageable);
}
