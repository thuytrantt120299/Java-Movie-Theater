package com.vn.service;

import org.springframework.data.domain.Page;

import com.vn.entity.CinemaRoom;
import com.vn.model.CinemaRoomDTO;

public interface CinemaRoomService {
	
		CinemaRoom saveCinemaRoom(CinemaRoomDTO dto);
	 
		Page<CinemaRoomDTO> getAll(Integer page, Integer size);

		CinemaRoomDTO findById(Integer id);
		
		Page<CinemaRoomDTO> searchCinemaRoombyName(String name, Integer page, Integer size);
}
