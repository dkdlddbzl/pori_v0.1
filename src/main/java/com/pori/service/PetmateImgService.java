package com.pori.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.pori.entity.PetmateImg;
import com.pori.repository.PetmateImgRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PetmateImgService {
	
	private String itemImgLocation = "C:/shop/item";
	
	private final PetmateImgRepository petmateImgRepository;
	
	private final FileService fileService;
	
	//이미지를 저장 1.파일을  itemImgLocation에 저장
	public void savePetmateImg(PetmateImg petmateImg, MultipartFile itemImgFile) throws Exception {
		String oriImgName = itemImgFile.getOriginalFilename(); //파일이름 - 이미지1.jpg
		String imgName = "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			imgUrl = "/images/item/" + imgName; 
		}
		
		//2.item_img 테이블에 저장 -> 이미지1.jpg, ASDIJSADK.jpg, "") entity값을 update
		petmateImg.updatePetmateImg(oriImgName, imgName, imgUrl);
		petmateImgRepository.save(petmateImg); //db에 insert
		
		
	}
	
	//이미지 업데이트 메소드
	public void updatePetmateImg(Long petmateImgId, MultipartFile petmateImgFile ) throws Exception {
		
		if(!petmateImgFile.isEmpty()) {
			PetmateImg savedPetmateImg = petmateImgRepository.findById(petmateImgId)
					.orElseThrow(EntityNotFoundException::new);
			
			
			if(!StringUtils.isEmpty(savedPetmateImg.getImgName())) {
				fileService.deleteFile(itemImgLocation + "/" + savedPetmateImg.getImgName());
			}
			
			String oriImgName = petmateImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(itemImgLocation, oriImgName, petmateImgFile.getBytes());
			String imgUrl = "/images/item/" + imgName;
			
			
			savedPetmateImg.updatePetmateImg(oriImgName, imgName, imgUrl);
		}
		
	}
	
	
	
	
	
	
	
	
	
}
