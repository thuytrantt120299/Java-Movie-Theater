package com.vn.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vn.entity.Account;
import com.vn.model.EmployeeDTO;
import com.vn.service.AccountService;
import com.vn.service.EmployeeService;
import com.vn.service.auth.CustomAccountDetails;

@Controller
public class EmployeeController {
	
	final static String PATH_FILE = "./src/main/resources/static/image/employee/";
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private EmployeeService service;

	@GetMapping("/employeeMNG/employee-list")
	public String viewEmployeeList(Model model,
			Authentication authentication,
			@RequestParam(value = "nameSearch", required = false) String nameSearch,
			@RequestParam(value="page", required = false, defaultValue = "1") Integer page,
			@RequestParam(value="size", required = false, defaultValue = "3") Integer size
			) {
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		if(nameSearch == null || "".equals(nameSearch)) {
			nameSearch = "";
			model.addAttribute("list",service.getAll(page-1,size));
		}else {
			model.addAttribute("list",service.searchEmployeeByName(nameSearch, page-1,size));			
		}
		model.addAttribute("nameSearch", nameSearch);
		return "/employeeMNG/employeeMNG-list";
	}
	
	

	@GetMapping("/employeeMNG/new-employee")
	public String showNewEmployeeForm(Model model,Authentication authentication) {
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		model.addAttribute("employee", new EmployeeDTO());
		return "employeeMNG/employeeMNG-add";
	}

	@PostMapping("/employeeMNG/save-employee")
	public String saveEmployee(@ModelAttribute(name = "employee") EmployeeDTO employeeDTO,
			@RequestParam("imageFile") MultipartFile multipartFile) throws IOException {
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			employeeDTO.setImage(fileName);
			Account savedEmployee = service.save(employeeDTO);
			String uploadDir = PATH_FILE + savedEmployee.getAccountId();
			Path uploadPath = Paths.get(uploadDir);
			
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			} else {
				deleteFilesInDir(uploadDir);
			}
			
			try {
				InputStream inputStream = multipartFile.getInputStream();

				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new IOException("Could not save uploaded file: " + fileName);
			} 
		} else {
			if (employeeDTO.getAccountId() != null) {
				service.save(employeeDTO);
			} else {
				employeeDTO.setImage("N/A");
				service.save(employeeDTO);
			}
			
		}
		return "redirect:/employeeMNG/employee-list";
	}
	
	@GetMapping("/employeeMNG/edit-employee/{accountId}")
	public String showEditPromotionForm(@PathVariable(name = "accountId") Long id, Model model,Authentication authentication) {
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		
		
		EmployeeDTO employeeDTO = service.get(id);
		model.addAttribute("employee", employeeDTO);
		return "employeeMNG/employeeMNG-save"; 
	}
	
	@GetMapping("/employeeMNG/delete-employee/{accountId}")
	public String deleteEmployee(@PathVariable(name = "accountId") Long id) throws IOException {
	
		if (!service.get(id).getImage().equals("N/A")) {
			String deleteDir = PATH_FILE + id;
			Path deletePath = Paths.get(deleteDir);
			deleteFilesInDir(deleteDir);
			Files.delete(deletePath);
		}
		
		service.delete(id);
		return "redirect:/employeeMNG/employee-list";
	}
	
	public void deleteFilesInDir(String path) {
		File directory = new File(path);
		
		String[] listFile = directory.list();
		
		if (listFile == null) {
			return;
		} else {
			for (String fileName : listFile) {
				File file = new File(directory, fileName);
				file.delete();
			}
		}
	}
}
