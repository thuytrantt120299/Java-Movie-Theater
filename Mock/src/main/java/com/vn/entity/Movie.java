package com.vn.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MOVIE")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MOVIE_ID", length = 10)
	private Integer id;
	
	@ManyToMany
	@JoinTable(
		name = "Movie_Type",
		joinColumns = {@JoinColumn(name = "MOVIE_ID")},
		inverseJoinColumns = {@JoinColumn(name = "TYPE_ID")}
		)
	private List<Type> types;

	@ManyToMany
	@JoinTable(
		name = "Movie_Schedule",
		joinColumns = {@JoinColumn(name = "MOVIE_ID")},
		inverseJoinColumns = {@JoinColumn(name = "SCHEDULE_ID")}
		)
	private List<Schedule> schedules;
	
	
	@ManyToMany
	@JoinTable(
		name = "Movie_ShowDate",
		joinColumns = {@JoinColumn(name = "MOVIE_ID")},
		inverseJoinColumns = {@JoinColumn(name = "SHOW_DATE_ID")}
		)
	private List<ShowDate> showDates;
	
	@Column(name = "ACTOR" , length = 255)
	private String actor;
	
	@Column(name = "CINEMA_ROOM_ID" , length = 10)
	private Integer cinemaRoomId;
	
	@Column(name = "CONTENT" , length = 1000)
	private String content;
	
	@Column(name = "DIRECTOR", length = 255)
	private String director;
	

	@Column(name = "DURATION", length = 10)
	private Integer duration;

//	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column(name = "FROM_DATE")
	private String fromDate;
	
	@Column(name = "MOVIE_PRODUCT_COMPANY", length = 255)
	private String movieProductCompany;

//	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column(name = "TO_DATE")
	private String toDate;
	
	@Column(name = "VERSION", length = 255)
	private String version;
	
	@Column(name = "MOVIE_NAME_ENGLISH", length = 255)
	private String movieNameEnglish;
	
	@Column(name = "MOVIE_NAME_VN", length = 255)
	private String movieNameVn;
	
	@Column(name = "LARGE_IMAGE", length = 255)
	private String largeImage;
	
	@Column(name = "SMALL_IMAGE", length = 255)
	private String smallImage;

}
