package com.vn.service;


import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vn.entity.Promotion;
import com.vn.model.PromotionDTO;
import com.vn.repository.PromotionRepository;

@Service
public class PromotionServiceImpl implements PromotionService {
	
	@Autowired
	private PromotionRepository promotionRepository; 

	@Override
	public Page<PromotionDTO> getAll(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page,size);
		Page<Promotion> entities = promotionRepository.findAll(pageable);
		Page<PromotionDTO> result = entities.map(new Function<Promotion, PromotionDTO>(){
			
			@Override
		public PromotionDTO apply(Promotion t) {
			PromotionDTO dto = new PromotionDTO();
			BeanUtils.copyProperties(t, dto);
			return dto;
			}
		});
		
		return result;
	};

	@Override
	public Promotion save(PromotionDTO dto) {
		Promotion promotion = new Promotion();
		promotion.setDetail(dto.getDetail());
		promotion.setDiscountLevel(dto.getDiscountLevel());
		promotion.setEndTime(dto.getEndTime());
		promotion.setId(dto.getId());
		promotion.setImage(dto.getImage());
		promotion.setStartTime(dto.getStartTime());
		promotion.setTitle(dto.getTitle());
		promotionRepository.save(promotion);
		return promotion;
	}

	@Override
	public PromotionDTO get(Integer id) {
		Promotion entity = promotionRepository.findById(id).orElse(null);
		//Su dung tien ich cua bean de copy thong tin tu entity sang dto
		PromotionDTO dto = new PromotionDTO();
		if(entity!=null) {
			BeanUtils.copyProperties(entity, dto);
		}
		
		return dto;
	}

	@Override
	public void delete(Integer id) {
		promotionRepository.deleteById(id);
	}

	@Override
	public Page<PromotionDTO> searchPromotionByTitle(String title, Integer page, Integer size) {
		String likeTitle = "%" + title + "%";
		Pageable pageable = PageRequest.of(page,size);
		Page<Promotion> pageEntity = promotionRepository.findByTitleLike(likeTitle, pageable);
		//convert page entity => page dto
		Page<PromotionDTO> result = pageEntity.map(new Function<Promotion, PromotionDTO>(){
		
			@Override
		public PromotionDTO apply(Promotion t) {
			PromotionDTO dto = new PromotionDTO();
			BeanUtils.copyProperties(t, dto);
			return dto;
			}
		});
		return result;
	}
	
}
