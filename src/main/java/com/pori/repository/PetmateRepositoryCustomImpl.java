package com.pori.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.pori.dto.MainPetmateDto;
import com.pori.dto.PetmateSearchDto;
import com.pori.dto.QMainPetmateDto;
import com.pori.entity.Petmate;
import com.pori.entity.QPetmate;
import com.pori.entity.QPetmateImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class PetmateRepositoryCustomImpl implements PetmateRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public PetmateRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	@Override
	public Page<Petmate> getAdminPetmatePage(PetmateSearchDto petmateSearchDto, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	private BooleanExpression petmateNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QPetmate.petmate.petmateNm.like("%" + searchQuery + "%");
	}
	
	
	
	
	@Override
	public Page<MainPetmateDto> getMainPetmatePage(PetmateSearchDto petmateSearchDto, Pageable pageable) {
		QPetmate petmate = QPetmate.petmate;
		QPetmateImg petmateImg = QPetmateImg.petmateImg;
		
		
		List<MainPetmateDto> content = queryFactory
				.select(
						new QMainPetmateDto(
								petmate.id,
								petmate.petmateNm,
								petmate.petmateDetail,
								petmateImg.imgUrl,
								petmate.price,
								petmate.runCount,
								petmate.likeCount)
						)
				.from(petmateImg)
				.join(petmateImg.petmate, petmate)
				.where(petmateImg.repimgYn.eq("Y"))
				.where(petmateNmLike(petmateSearchDto.getSearchQuery()))
				.orderBy(petmate.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
				
		
		long total = queryFactory
				.select(Wildcard.count)
				.from(petmateImg)
				.join(petmateImg.petmate, petmate)
				.where(petmateImg.repimgYn.eq("Y"))
				.where(petmateNmLike(petmateSearchDto.getSearchQuery()))
				.fetchOne()
				;
		
		return new PageImpl<>(content, pageable, total);
	}

}
