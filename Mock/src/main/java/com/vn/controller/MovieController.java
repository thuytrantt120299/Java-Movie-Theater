package com.vn.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vn.entity.Account;
import com.vn.entity.CinemaRoom;
import com.vn.entity.Movie;
import com.vn.entity.Schedule;
import com.vn.entity.Type;
import com.vn.model.MovieDTO;
import com.vn.repository.CinemaRoomRepository;
import com.vn.repository.ScheduleRepository;
import com.vn.repository.TypeRepository;
import com.vn.service.AccountService;
import com.vn.service.MovieService;
import com.vn.service.auth.CustomAccountDetails;

@Controller
public class MovieController {

	final static String PATH_FILE = "/WEB-INF/image/movie/";

	@Autowired
	private MovieService movieService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private CinemaRoomRepository cinemaroomRepository;

	@GetMapping(value = { "/movieMNG", "/movieMNG/movie-list" })
	public String listMovie(Model model, Authentication authentication,
			@RequestParam(value = "titlesearch", required = false) String titlesearch,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "3") Integer size) {

		if (authentication != null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}


		if (titlesearch == null || "".equals(titlesearch)) {
			titlesearch = "";
			model.addAttribute("list", movieService.getAll(page - 1, size));
		} else {
			model.addAttribute("list", movieService.searchMovieByName(titlesearch, page - 1, size));
		}
		model.addAttribute("titlesearch", titlesearch);
		return "movieMNG/movie-list";
	}

	@GetMapping("/movieMNG/movie-add")
	public String saveMovie(Model model, Authentication authentication) {
		if (authentication != null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		List<Type> types = typeRepository.findAll();
		model.addAttribute("allTypes", types);
		List<Schedule> schedules = scheduleRepository.findAll();
		
		List<CinemaRoom> cinemaRooms = cinemaroomRepository.findAll();
		model.addAttribute("cinemaRooms", cinemaRooms);
		model.addAttribute("allSchedules", schedules);
		
		model.addAttribute("movieDTO", new MovieDTO());
		
		model.addAttribute("cinemaRooms", cinemaRooms);
		
		return "movieMNG/movie-add";
	}

	@PostMapping("/movieMNG/movie-add")
	public String savedMovie(@Valid  @ModelAttribute("movieDTO") MovieDTO dto,
			BindingResult results, RedirectAttributes reAttributes,
			@RequestParam("imageFile") MultipartFile multipartFile,
			HttpServletRequest request) throws IOException, ParseException {

		
		if(results.hasErrors()) {
			return "movieMNG/movie-add";
		}


		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			dto.setLargeImage(fileName);
			Movie saveMovie = movieService.saveMovie(dto);
			try {
				//1. create folder promotion/1
				String uploadRootPath = request.getServletContext().getRealPath(PATH_FILE) + saveMovie.getId();
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
			if (dto.getId() != null) {
				movieService.saveMovie(dto);
			} else {
				dto.setLargeImage("N/A");
				movieService.saveMovie(dto);
			}
			
		}

		
		reAttributes.addFlashAttribute("message", "Add movie: " + dto.getMovieNameEnglish()+ " to list successfully");

		return "redirect:/movieMNG";
		
	}

	@GetMapping("/movieMNG/movie-update/{id}")
	public String updateMovie(Model model, @PathVariable(name = "id") Integer id, Authentication authentication) {
		if (authentication != null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		List<Type> allTypes = typeRepository.findAll();
		model.addAttribute("allTypes", allTypes);
		List<Schedule> schedules = scheduleRepository.findAll();
		model.addAttribute("allSchedules", schedules);
		List<CinemaRoom> cinemaRooms = cinemaroomRepository.findAll();
		model.addAttribute("cinemaRooms", cinemaRooms);
		MovieDTO movie = movieService.getById(id);
		model.addAttribute("updateMovie", movie);
		return "/movieMNG/movie-update";
	}
	

	@PostMapping("/movieMNG/movie-update")
	public String updatedMovie(@Valid @ModelAttribute("movieDTO") MovieDTO dto,
			BindingResult results, RedirectAttributes reAttributes,
			@RequestParam("imageFile") MultipartFile multipartFile
			, HttpServletRequest request) throws IOException, ParseException {
		
		if(results.hasErrors()) {
			return "movieMNG/movie-update";
		}
		
		

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			dto.setLargeImage(fileName);
			Movie saveMovie = movieService.saveMovie(dto);
			
			try {
				//1. create folder promotion/1
				String uploadRootPath = request.getServletContext().getRealPath(PATH_FILE) + saveMovie.getId();
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
			if (dto.getId() != null) {
				movieService.saveMovie(dto);
			} else {
				dto.setLargeImage("N/A");
				movieService.saveMovie(dto);
			}
			
		}
		
		reAttributes.addFlashAttribute("message", "Update movie: " + dto.getMovieNameEnglish()+ " to list successfully");
		return "redirect:/movieMNG";
		
	}

	@GetMapping("/movieMNG/movie-delete/{id}")
	public String deleteMovie(@PathVariable("id") Integer id,
			HttpServletRequest request) throws IOException {
		
//		if (!movieService.getById(id).getLargeImage().equals("N/A")) {
//			String deleteDir = PATH_FILE + id;
//			Path deletePath = Paths.get(deleteDir);
//			deleteFilesInDir(deleteDir);
//			Files.delete(deletePath);
//		}
		if (!movieService.getById(id).getLargeImage().equals("N/A")) {
			String deleteDir = request.getServletContext().getRealPath(PATH_FILE) + id;
			Path deletePath = Paths.get(deleteDir);
			deleteFilesInDir(deleteDir);
			Files.delete(deletePath);
		}
		
		movieService.deleteById(id);
		return "redirect:/movieMNG";
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
