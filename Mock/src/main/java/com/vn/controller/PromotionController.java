package com.vn.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
import com.vn.entity.Promotion;
import com.vn.model.PromotionDTO;
import com.vn.service.AccountService;
import com.vn.service.PromotionService;
import com.vn.service.auth.CustomAccountDetails;

@Controller
public class PromotionController {
	
	final static String PATH_FILE = "/WEB-INF/image/promotion/";
	
	@Autowired
	ServletContext context;
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private PromotionService service;

	@GetMapping("/promotionMNG/promotion-list")
	public String viewPromotionList(Model model,
			Authentication authentication,
			@RequestParam(value = "titlesearch", required = false) String titlesearch,
			@RequestParam(value="page", required = false, defaultValue = "1") Integer page,
			@RequestParam(value="size", required = false, defaultValue = "3") Integer size
			) {
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		if(titlesearch == null || "".equals(titlesearch)) {
			titlesearch = "";
			model.addAttribute("list",service.getAll(page-1,size));
		}else {
			model.addAttribute("list",service.searchPromotionByTitle(titlesearch,page-1,size));			
		}
		model.addAttribute("titlesearch", titlesearch);
		return "/promotionMNG/promotion-list";
	}
	
	
//
	@GetMapping("/promotionMNG/new-promotion")
	public String showNewPromotionForm(Model model,Authentication authentication) {
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		model.addAttribute("promotion", new PromotionDTO());
		return "promotionMNG/promotion-add";
	}

	@PostMapping("/promotionMNG/save-promotion")
	public String savePromotion(@ModelAttribute(name = "promotion") PromotionDTO promotion,
			@RequestParam("imageFile") MultipartFile multipartFile,
			HttpServletRequest request) throws IOException {
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			promotion.setImage(fileName);
			Promotion savedPromotion = service.save(promotion);
			
			try {
				//1. create folder promotion/1
				String uploadRootPath = request.getServletContext().getRealPath(PATH_FILE) + savedPromotion.getId();
				Path uploadDir = Paths.get(uploadRootPath);
				
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				} else {
					deleteFilesInDir(uploadRootPath);
				}
			   
			   //2. Create file server
               File serverFile = new File(new File(uploadRootPath).getAbsolutePath() + File.separator + fileName);
               
               //3. save file
               multipartFile.transferTo(serverFile);
               
			} catch (IOException e) {
				throw new IOException("Could not save uploaded file: " + fileName);
			} 
		} else {
			if (promotion.getId() != null) {
				service.save(promotion);
			} else {
				promotion.setImage("N/A");
				service.save(promotion);
			}
			
		}
		return "redirect:/promotionMNG/promotion-list";
	}
	
	@GetMapping("/promotionMNG/edit-promotion/{id}")
	public String showEditPromotionForm(@PathVariable(name = "id") Integer id, Model model,Authentication authentication) {
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		
		
		PromotionDTO promotion = service.get(id);
		model.addAttribute("promotion", promotion);
		return "promotionMNG/promotion-edit";
	}
	
	@GetMapping("/promotionMNG/delete-promotion/{id}")
	public String deletePromotion(@PathVariable(name = "id") Integer id,
			HttpServletRequest request) throws IOException {
	
		if (!service.get(id).getImage().equals("N/A")) {
			String deleteDir = request.getServletContext().getRealPath(PATH_FILE) + id;
			Path deletePath = Paths.get(deleteDir);
			deleteFilesInDir(deleteDir);
			Files.delete(deletePath);
		}
		service.delete(id);
		return "redirect:/promotionMNG/promotion-list";
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
