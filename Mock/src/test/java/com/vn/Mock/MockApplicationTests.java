package com.vn.Mock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vn.entity.Account;
import com.vn.entity.Role;
import com.vn.service.AccountService;
import com.vn.service.RoleService;

@SpringBootTest
class MockApplicationTests {

	@Autowired
	private AccountService accountServiceImpl;
	
	@Autowired
	private RoleService roleServiceImpl;
	
	
	@Test
	 void testCreateAdminAccount() {
		Role roleAdmin = roleServiceImpl.findByName("Admin");
		Account account = new Account();
		account.setUsername("testAdmin");
		account.setPassword("admin123");
		account.setFullName("Ravi");
		account.setAddress("Timecity");
		account.setRole(roleAdmin);
		Account savedAccount = accountServiceImpl.save(account);
		System.out.println(savedAccount.toString());
	}
	

}
