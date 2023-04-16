package com.vn.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long accountId;
	
	@NotBlank(message = "{account.address.notblank}")
	@Column(name = "address", length = 255)
	private String address;
	
//	@NotBlank(message = "{account.dob.notblank}")
	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date dateOfBirth;
	
	@NotBlank(message = "{account.email.notblank}")
	@Email(message = "{account.email.validate}")
	@Column(name = "email", length = 255)
	private String email;
	
	@NotBlank(message = "{account.fullName.notblank}")
	@Column(name = "full_name", length = 255)
	private String fullName;
	
	@NotBlank(message = "{account.gender.notblank}")
	@Column(name = "gender", length = 255)
	private String gender;
	
	@NotBlank(message = "{account.identityCard.notblank}")
	@Column(name = "identity_card", length = 255)
	private String identityCard;
	
	@Column(name = "image", length = 255)
	private String image;
	
	@Transient
    public String getPhotosImagePath() {
        if (image == null || accountId == null) return null;
         
        return "/account-images/" + accountId + "/" + image;
    }
	
	@NotBlank(message = "{account.password.notblank}")
	@Column(name = "password", length = 255)
	private String password;
	
//	@NotBlank(message = "{account.rpassword.notblank}")
//	private String rpassword;

	@NotBlank(message = "{account.phoneNumber.notblank}")
	@Column(name = "phone_number", length = 255)
	private String phoneNumber;
	
	@Column(name = "register_date")
	@Temporal(TemporalType.DATE)
	private java.util.Date registerDate;
	
	@Column(name = "status")
	private Integer status;
	
	@NotBlank(message = "{account.username.notblank}")
	@Column(name = "username", length = 255)
	private String username;
	
	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	private Role role;
	
	@OneToMany (cascade = CascadeType.ALL, mappedBy="account", fetch=FetchType.EAGER)
	private Set<Invoice> invoices;

	
}
