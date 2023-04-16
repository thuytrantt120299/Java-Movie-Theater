package com.vn.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "CINEMA_ROOM")
public class CinemaRoom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CINEMA_ROOM_ID", length = 10)
	private Integer id;
	
	@OneToMany(mappedBy = "cinemaRoomId", cascade = CascadeType.ALL )
	private List<Seat> seats;
	
	@Column(name = "CINEMA_ROOM_NAME", length = 255)
	private String name;
	

	@Column(name = "NUMBER_SEAT_ROW", length = 10)
	private Integer row;
	
	@Column(name = "NUMBER_SEAT_COLUMN", length = 10)
	private Integer column;

}
