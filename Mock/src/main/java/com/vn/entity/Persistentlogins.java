package com.vn.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="persistent_logins")
public class Persistentlogins {
	@Id
	@Column(name = "series", length = 64, nullable = false)
	private	String series;
	
	@Column(name = "username", length = 64, nullable = false)
	private String username;
	
	@Column(name = "token", length = 64, nullable = false)
	private String token;
	
	@Column(name = "last_used", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUse;
}
