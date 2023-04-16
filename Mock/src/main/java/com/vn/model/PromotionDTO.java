package com.vn.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {
	@Id
	@Column(length = 10)
	private Integer id;
	
	@Column(length = 255)
	private String detail;
	
	@Column(length = 10)
	private Integer discountLevel;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	
	@Column(length = 255)
	private String image;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	
	@Column(length = 255)
	private String title;
}
