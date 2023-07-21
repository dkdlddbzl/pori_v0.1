package com.pori.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.pori.dto.ReviewFormDto;
import com.pori.entity.Member;
import com.pori.entity.Petmate;
import com.pori.entity.Review;
import com.pori.repository.MemberRepository;
import com.pori.repository.PetmateImgRepository;
import com.pori.repository.PetmateRepository;
import com.pori.repository.ReviewRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
		private final PetmateRepository petmateRepository;
		private final MemberRepository memberRepository;
		private final ReviewRepository reviewRepository;
		private final PetmateImgRepository petmateImgRepository;
		
		
		//리뷰 남기기
		public Long saveReview(ReviewFormDto reviewFormDto) throws Exception {
			
			Petmate petmate = petmateRepository.findById(reviewFormDto.getPetmateId()).orElseThrow(EntityNotFoundException::new);
			
			Review review = reviewFormDto.createReview(petmate);
			reviewRepository.save(review);
			petmate.updateStatus();
			
			return review.getId();
		}
		
		@Transactional(readOnly = true)
		public ReviewFormDto getReviewDtl(Long reviewId) {
			Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
			
			ReviewFormDto reviewFormDto = ReviewFormDto.of(review);
			
			return reviewFormDto;
			
		}
		
		
		//리뷰 삭제
		public void deleteReview(Long reviewId) {
			Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
			
			reviewRepository.delete(review);
			
		}
		
		
		
		
		
		
		
		
		
		
		
}
