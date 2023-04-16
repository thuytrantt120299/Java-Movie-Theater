package com.vn.model;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.vn.entity.Invoice;
import com.vn.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

	private Long accountId; 

	private String address;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date dateOfBirth;
	
	private String email;
	
	private String fullName;
	
	private String gender;

	private String identityCard;
	
	private String image;
	
	private String password;
	
	private String phoneNumber;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date registerDate;
	
	private Integer status;
	
	private String username;
	
	private Role role;
	
	private Set<Invoice> invoices;
}
