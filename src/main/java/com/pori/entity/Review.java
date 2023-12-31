package com.pori.entity;



import java.time.format.DateTimeFormatter;

import com.pori.dto.ReviewFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="reviews")
@Getter
@Setter
@ToString
public class Review extends BaseEntity {
	
	@Id
	@Column(name="review_id") //테이블로 생성될때 컬럼이름을 지정해준다
	@GeneratedValue(strategy = GenerationType.AUTO) //기본키를 자동으로 생성해주는 전략 사용
	private Long id;
	
	@Lob // clob과 같은 큼타입이 문자타입으로 컬럼은 만든다
	@Column(columnDefinition = "longtext")
	private String reviewDtl;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="petmate_id")
	private Petmate petmate;
	
	
	
	public void updateReview(ReviewFormDto reviewFormDto) {
		this.reviewDtl = reviewFormDto.getReviewDtl();
	}
	
	
	
}
