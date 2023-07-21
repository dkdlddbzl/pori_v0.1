package com.pori.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pori.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	
	@Query("select i from Book i where i.member.email = : email order by i.booksOpen desc")
	List<Book> findBooks(@Param("email") String email, Pageable pageable);
	
	
	@Query("select count(i) from Book i where i.member.email = :email")
	Long countBook(@Param("email") String email);	
	
	
}
