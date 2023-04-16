package com.vn.entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "SCHEDULE")
public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SCHEDULE_ID", length = 10)
	private Integer id;

	@Column(name = "SCHEDULE_TIME")
	private String time;

	@ManyToMany(mappedBy = "schedules", cascade = CascadeType.ALL)
	private List<Movie> movies;
	
	public Schedule(Integer id,String time) {
		this.id = id;
		this.time = time;
	}

	public Schedule() {

	}





}
