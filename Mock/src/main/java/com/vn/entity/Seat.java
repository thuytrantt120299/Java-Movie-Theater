package com.vn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@Table(name = "SEAT")
public class Seat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEAT_ID", length = 10)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "CINEMA_ROOM_ID", referencedColumnName = "CINEMA_ROOM_ID")
	private CinemaRoom cinemaRoomId;
	
	@Column(name = "SEAT_COLUMN", length = 255)
	private String column;
	
	@Column(name = "SEAT_ROW", length = 10)
	private Integer row;
	
	@Column(name = "SEAT_STATUS", length = 1)
	private Integer status;
	
	@Column(name = "SEAT_TYPE", length = 1)
	private Integer type;
	
	
}
