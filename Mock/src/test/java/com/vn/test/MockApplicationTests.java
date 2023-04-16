package com.vn.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vn.entity.Account;
import com.vn.entity.Role;
import com.vn.model.InvoiceDto;
import com.vn.service.AccountService;
import com.vn.service.InvoiceService;
import com.vn.service.RoleService;

@SpringBootTest
class MockApplicationTests {

	@Autowired
	private AccountService accountServiceImpl;
	
	@Autowired
	private RoleService roleServiceImpl;
	
	@Autowired
	private InvoiceService invoiceServiceImpl;
	
	
	@Test
	public void testCreateInvoice() {
		Account account = accountServiceImpl.findById(17l);
		System.out.println(account);
		InvoiceDto invoiceDto = new InvoiceDto();
		invoiceDto.setMovieName("Movie name 3");
		

		invoiceDto.setBookingDate(Date.valueOf(LocalDate.of(2020, 10, 15)));
		
//		invoiceDto.setBookingDate(new java.util.Date());
		invoiceDto.setAddScore(2);
//		invoiceDto.setScheduleShow(LocalDateTime.of(2021, 12, 15,10,20,20));
		invoiceDto.setScheduleShow(Date.valueOf(LocalDate.of(2021, 10, 15)));
		invoiceDto.setScheduleShowTime("show time");
		invoiceDto.setStatus(1);	
		invoiceDto.setTotalMoney(123.03);
		invoiceDto.setUseScore(22);
		invoiceDto.setSeat("seat");
		System.out.println(invoiceDto);
		
		invoiceServiceImpl.saveInvoice(invoiceDto, account);
	}
	
	
	
	@Test
	 void testCreateAdminAccount() {
		Role roleAdmin = roleServiceImpl.findByName("Admin");
		Account account = new Account();
		account.setUsername("testAdmin2");
		account.setPassword("admin123");
		account.setFullName("Admin fullname");
		account.setAddress("Timecity");
		account.setEmail("admin@gmail.com");
		account.setGender("Female");
		account.setIdentityCard("123456");
		account.setPhoneNumber("09671283");
		account.setRole(roleAdmin);
		Account savedAccount = accountServiceImpl.save(account);
		System.out.println(savedAccount.toString());
	}
	

}
