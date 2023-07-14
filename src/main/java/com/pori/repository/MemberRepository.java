package com.pori.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pori.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Long>{

	//select * from member where email = ?
	
	Member findByEmail(String email);
	
	//select * from member where member_id = ?
}
