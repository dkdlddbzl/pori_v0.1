package com.pori.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pori.dto.FaqFormDto;
import com.pori.entity.Faq;
import com.pori.entity.Member;
import com.pori.repository.FaqRepository;
import com.pori.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FaqService {
	private final FaqRepository faqRepository;
	private final MemberRepository memberRepository;
	
	//faq 테이블에 faq등록(insert)
	public Long saveFaq(FaqFormDto faqFormDto, String email) {
		
		
		Member member = memberRepository.findByEmail(email);
		
		
		
		Faq faq = faqFormDto.createFaq(member);
		faqRepository.save(faq);
		
		
		return faq.getId();
		
	}
	
	//faq 가져오기
	@Transactional(readOnly = true)
	public Page<Faq> getMainFaqDtl(Pageable pageable) {
		
		Page<Faq> faqPage = faqRepository.findAllByOrderByIdDesc(pageable);
		
		Long totalCount = faqRepository.count();
		
		
		
		return faqPage;
		
	}
	
	
}
