package com.pori.entity;

import java.util.ArrayList;
import java.util.List;

import com.pori.constrant.BookStatus;
import com.pori.constrant.ReserveStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="books")
@Getter
@Setter
@ToString
public class Book extends BaseEntity {
	
	@Id
	@Column(name="book_id") //테이블로 생성될때 컬럼이름을 지정해준다
	@GeneratedValue(strategy = GenerationType.AUTO) //기본키를 자동으로 생성해주는 전략 사용
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="petmate_id")
	private Petmate petmate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@Enumerated(EnumType.STRING)
	private BookStatus bookStatus;
	
	private String booksOpen;
	
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<BookPetmate> bookPetmates = new ArrayList<>();
	
	
	public void addBookPetmate(BookPetmate bookPetmate) {
		this.bookPetmates.add(bookPetmate);
		bookPetmate.setBook(this);
	}
	
	//book 객체 생성
	public static Book createBook(Member member, List<BookPetmate> bookPetmateList, String booksOpen, Petmate petmate
			, BookStatus bookStatus) {
		Book book = new Book();
		book.setMember(member);
		book.setBooksOpen(booksOpen);
		book.setPetmate(petmate);
		book.setBookStatus(bookStatus);
		
		for(BookPetmate bookPetmate : bookPetmateList) {
			book.addBookPetmate(bookPetmate);
		}
		
		
		
		return book;
	}
	
	
	public void cancelBook() {
		this.bookStatus = bookStatus.N;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
