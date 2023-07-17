package com.pori.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pori.dto.MainPetmateDto;
import com.pori.dto.PetmateFormDto;
import com.pori.dto.PetmateImgDto;
import com.pori.dto.PetmateSearchDto;
import com.pori.entity.Petmate;
import com.pori.entity.PetmateImg;
import com.pori.repository.PetmateImgRepository;
import com.pori.repository.PetmateRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PetmateService {
	private final PetmateRepository petmateRepository;
	private final PetmateImgService petmateImgService;
	private final PetmateImgRepository petmateImgRepository;
	
	//petmate 테이블에 상품등록(insert)
	public Long savePetmate(PetmateFormDto petmateFormDto, List<MultipartFile> petmateImgFileList) throws Exception {
		
		//1. 상품등록
		Petmate petmate = petmateFormDto.createPetmate();
		petmateRepository.save(petmate); //저장
		
		
		
		//2. 이미지 등록
		for(int i=0; i< petmateImgFileList.size(); i++) {
			PetmateImg petmateImg = new PetmateImg();
			petmateImg.setPetmate(petmate);
		
			if(i==0) {
				petmateImg.setRepimgYn("Y");
			} else {
				petmateImg.setRepimgYn("N");
			}
		
			
			petmateImgService.savePetmateImg(petmateImg, petmateImgFileList.get(i));
		}
		
		return petmate.getId();
		
	}
	
	//상품 가져오기
	@Transactional(readOnly = true) //트랜잭션 읽기 전용 (변경 감지 수행하지 않음) -> 성능 향상
	public PetmateFormDto getPetmateDtl(Long petmateId) {
		//1. petmate_img 테이블의 이미지를 가져온다
		List<PetmateImg> petmateImgList = petmateImgRepository.findByPetmateIdOrderByIdAsc(petmateId);
		
		
		//PetmateImg 엔티티 객체 -> PetmateImgDto로 변환
		List<PetmateImgDto> petmateImgDtoList = new ArrayList<>();
		
		for(PetmateImg petmateImg : petmateImgList) {
			PetmateImgDto petmateImgDto = PetmateImgDto.of(petmateImg);
			petmateImgDtoList.add(petmateImgDto);
		}
		
		
		
		//2. petmate 테이블에 있는 데이터를 가져온다
		Petmate petmate = petmateRepository.findById(petmateId).orElseThrow(EntityNotFoundException::new);
		
		
		//Petmate엔티티 객체 -> Dto로 변환
		PetmateFormDto petmateFormDto = PetmateFormDto.of(petmate);
		
		//3. PetmateFormDto에 이미지 정보(petmateImgDtoList)를 넣어준다
		petmateFormDto.setPetmateImgDtoList(petmateImgDtoList);
		
		return petmateFormDto;
	}
	
	//1. petmate 엔티티를 가져와서 업데이트
	public Long updatePetmate(PetmateFormDto petmateFormDto, List<MultipartFile> petmateImgFileList) throws Exception {
		Petmate petmate = petmateRepository.findById(petmateFormDto.getId()).orElseThrow(EntityNotFoundException::new);
		
		petmate.updatePetmate(petmateFormDto);
		
		
		//2. petmate_img를 바꿔준다 -> 5개 전부 변경
		List<Long> petmateImgIds = petmateFormDto.getPetmateImgIds(); //이미지 아이디 리스트
		
		for(int i = 0; i< petmateImgFileList.size(); i++) {
			petmateImgService.updatePetmateImg(petmateImgIds.get(i), petmateImgFileList.get(i));
		}
		
		return petmate.getId();
	}
	
	
	@Transactional(readOnly = true)
	public Page<Petmate> getAdminPage(PetmateSearchDto petmateSearchDto, Pageable pageable) {
		Page<Petmate> petmatePage = petmateRepository.getAdminPetmatePage(petmateSearchDto, pageable);
		return petmatePage;
	}
	
	
	
	
	@Transactional(readOnly = true)
	public Page<MainPetmateDto> getMainPetmatePage(PetmateSearchDto petmateSearchDto, Pageable pageable) {
		Page<MainPetmateDto> mainPetmatePage = petmateRepository.getMainPetmatePage(petmateSearchDto, pageable);
		return mainPetmatePage;
	}
	
	
	
	
	
	
	
	
	
}
