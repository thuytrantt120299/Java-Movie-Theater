package com.vn.model;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
	private Long invoiceId;

	private Integer addScore;
	
	private Date bookingDate;
	
	private String movieName;
	
	private Date scheduleShow;
	
	private String scheduleShowTime;
	
	private Integer status;
	
	private Double totalMoney;
	
	private Integer useScore;
	
	private String seat;
	
}
