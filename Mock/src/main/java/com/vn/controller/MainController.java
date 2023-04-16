package com.vn.controller;



import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.vn.entity.Account;
import com.vn.service.AccountService;
import com.vn.service.MovieService;
import com.vn.service.auth.CustomAccountDetails;

@Controller
public class MainController {
	@Autowired
	private AccountService accountService;
	@Autowired
	MovieService movieService;

	@GetMapping("/login")
	public String login() {

		return "login";
	}

	@GetMapping(value = { "/home", "/" })
	public String home(Model model, Authentication authentication) {
		if (authentication != null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  
		String strDate = formatter.format(new Date());
		model.addAttribute("listMovie",movieService.findByDate(strDate));
		return "index";

	}

	@GetMapping("/registration")
	public String registrationForm(Model model) { 
		model.addAttribute("account", new Account());
		return "registration";
	}

	@PostMapping("/registration")
	public String createTeam(@Valid @ModelAttribute("account") Account account, BindingResult result) {
		
		//check if the account exists
		if(accountService.accountExists(account.getUsername())) {
			result.addError(new FieldError("account","username","Username already in use"));
		}
//		//check if the passwords match
//		if(account.getPassword()!=null &account.getRpassword()!=null) {
//			if(!account.getPassword().equals(account.getRpassword())) {
//				result.addError(new FieldError("account","rpassword","Password must match"));
//			}
//		}
		// form validate
		if (result.hasErrors()) {
			return "registration";
		}
		accountService.registerDefaultAccount(account);
		return "redirect:/login";
	}
	
	
	

	
	

}
