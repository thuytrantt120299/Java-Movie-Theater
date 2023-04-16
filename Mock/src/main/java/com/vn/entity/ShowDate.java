package com.vn.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "SHOW_DATE")
public class ShowDate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SHOW_DATE_ID", length = 10)
	private Integer id;

	@ManyToMany(mappedBy = "showDates" , cascade = CascadeType.ALL)
	private List<Movie> movies;
	
	@Column(name = "SHOW_DATE")
	private LocalDate date;

	@Column(name = "DATE_NAME", length = 255)
	private String name;

	public ShowDate(String name, LocalDate date) {
		this.name = name;
		this.date = date;
	}

	public ShowDate() {
	}

}
