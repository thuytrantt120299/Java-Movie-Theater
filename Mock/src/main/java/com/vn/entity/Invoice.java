package com.vn.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name="invoice")
public class Invoice{
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "invoice_id")
	private Long invoiceId;
	
	@ManyToOne
	@JoinColumn(name= "account_id", referencedColumnName= "account_id")
	private Account account;
	
	@Column(name = "add_score")
	private Integer addScore;
	
	@Column(name = "booking_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bookingDate;
	
	@Column(name = "movie_name", length = 255)
	private String movieName;
	
	@Column(name = "schedule_show")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date scheduleShow;
	
	@Column(name = "schedult_show_time", length = 255)
	private String scheduleShowTime;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "total_money", precision = 19)
	private Double totalMoney;
	
	@Column(name = "use_score")
	private Integer useScore;
	
	@Column(name = "seat", length = 255)
	private String seat;
	
	
}
