package com.pori.entity;

import com.pori.constrant.BookStatus;

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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="books_petmate")
@Getter
@Setter
@ToString
public class BookPetmate {
	
	@Id
	@Column(name="book_petmate_id") //테이블로 생성될때 컬럼이름을 지정해준다
	@GeneratedValue(strategy = GenerationType.AUTO) //기본키를 자동으로 생성해주는 전략 사용
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="petmate_id")
	private Petmate petmate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="book_id")
	private Book book;
	
	@Enumerated(EnumType.STRING)
	private BookStatus bookStatus;
	
	private String booksOpen;
	
	
	public static BookPetmate createBookPetmate(Petmate petmate, String booksOpen, BookStatus bookStatus ) {
		BookPetmate bookPetmate = new BookPetmate();
		bookPetmate.setPetmate(petmate);
		bookPetmate.setBooksOpen(booksOpen);
		bookPetmate.setBookStatus(bookStatus);
		
		return bookPetmate;
	}
	
	
	
	
}
