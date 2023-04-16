package com.vn.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import com.vn.entity.Seat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaRoomDTO {
	@Id
	@Column(length = 10)
	private Integer id;
	
	private List<Seat> seats;
	
	@Column(length = 255)
	private String name;
	
	@Column(length = 10)
	private Integer row;
	@Column(length = 10)
	private Integer column;
}
