package com.vn.service;

import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vn.entity.CinemaRoom;
import com.vn.model.CinemaRoomDTO;
import com.vn.repository.CinemaRoomRepository;

@Service
public class CinemaRoomServiceImpl implements CinemaRoomService{
	@Autowired
	CinemaRoomRepository cinemaRoomRepository;
	
	@Override
	public CinemaRoom saveCinemaRoom(CinemaRoomDTO dto) {
		CinemaRoom entity = new CinemaRoom();
		BeanUtils.copyProperties(dto, entity);
		cinemaRoomRepository.save(entity);
		return entity;
	}

	@Override
	public Page<CinemaRoomDTO> getAll(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page,size);
		Page<CinemaRoom> entities = cinemaRoomRepository.findAll(pageable);
		Page<CinemaRoomDTO> result = entities.map(new Function<CinemaRoom, CinemaRoomDTO>(){
			
			@Override
		public CinemaRoomDTO apply(CinemaRoom t) {
			CinemaRoomDTO dto = new CinemaRoomDTO();
			BeanUtils.copyProperties(t, dto);
			return dto;
			}
		});
		
		return result;
	}

	@Override
	public CinemaRoomDTO findById(Integer id) {
		CinemaRoom entity = cinemaRoomRepository.findById(id).orElse(null);
		//Su dung tien ich cua bean de copy thong tin tu entity sang dto
		CinemaRoomDTO dto = new CinemaRoomDTO();
		if(entity!=null) {
			BeanUtils.copyProperties(entity, dto);
		}
		
		return dto;
	}

	@Override
	public Page<CinemaRoomDTO> searchCinemaRoombyName(String name, Integer page, Integer size) {
		String likeName = "%" + name + "%";
		Pageable pageable = PageRequest.of(page,size);
		Page<CinemaRoom> pageEntity = cinemaRoomRepository.findByNameLike(likeName, pageable);
		//convert page entity => page dto
		Page<CinemaRoomDTO> result = pageEntity.map(new Function<CinemaRoom, CinemaRoomDTO>(){
		
			@Override
		public CinemaRoomDTO apply(CinemaRoom t) {
				CinemaRoomDTO dto = new CinemaRoomDTO();
			BeanUtils.copyProperties(t, dto);
			return dto;
			}
		});
		return result;
	}


}
