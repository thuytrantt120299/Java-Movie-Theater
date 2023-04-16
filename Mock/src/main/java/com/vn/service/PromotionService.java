package com.vn.service;

import org.springframework.data.domain.Page;

import com.vn.entity.Promotion;
import com.vn.model.PromotionDTO;

public interface PromotionService {
	
	public Page<PromotionDTO> getAll(Integer page, Integer size);
	
	public Promotion save(PromotionDTO promotionDTO);
	
	public PromotionDTO get(Integer id);
	
	public void delete(Integer id);
	
	public Page<PromotionDTO> searchPromotionByTitle(String title, Integer page, Integer size);
	
}
