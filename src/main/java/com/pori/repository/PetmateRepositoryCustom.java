package com.pori.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pori.dto.MainPetmateDto;
import com.pori.dto.PetmateSearchDto;
import com.pori.entity.Petmate;

public interface PetmateRepositoryCustom {
	Page<Petmate> getAdminPetmatePage(PetmateSearchDto petmateSearchDto, Pageable pageable);
	
	Page<MainPetmateDto> getMainPetmatePage(PetmateSearchDto petmateSearchDto, Pageable pageable);
	
}
