package com.vn.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.entity.Seat;
import com.vn.repository.SeatRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cinemaRoom")
public class CinemaRoomApi {
	 @Autowired
	 SeatRepository seatRepository;
	 
	 @PostMapping
	 public ResponseEntity add(@RequestBody Integer seatId) {
		 Seat seat = seatRepository.getById(seatId);
		 Integer seatType = seat.getStatus();
		 if(seatType == 1) {
			 seat.setType(2);
		 }else {
			 seat.setType(1);
		 }
		 seatRepository.save(seat);
		 return new ResponseEntity<>(HttpStatus.OK);
	 }
}
