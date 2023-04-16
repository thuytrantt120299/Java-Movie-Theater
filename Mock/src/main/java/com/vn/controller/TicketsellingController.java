package com.vn.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vn.entity.Account;
import com.vn.entity.CinemaRoom;
import com.vn.entity.Schedule;
import com.vn.entity.ScheduleSeat;
import com.vn.model.ListScheduleSeatDTO;
import com.vn.model.MovieDTO;
import com.vn.repository.CinemaRoomRepository;
import com.vn.repository.ScheduleRepository;
import com.vn.repository.ScheduleSeatRepository;
import com.vn.repository.ShowDatesRepository;
import com.vn.service.AccountService;
import com.vn.service.MovieService;
import com.vn.service.auth.CustomAccountDetails;

@Controller
public class TicketsellingController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	ScheduleSeatRepository scheduleSeatRepository;
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	@Autowired
	ShowDatesRepository showdatesRepository;
	
	@Autowired
	CinemaRoomRepository cinemaRoomRepository;
	
	@GetMapping("/ticketsellingMNG/showtime")
	public String getShowTime(Model model,
			Authentication authentication,
			@RequestParam(value = "datesearch", required = false) String datesearch) throws ParseException {
		
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		// get date in show time
		Date startDate = new Date();
		Date datePlus1 = DateUtils.addDays(new Date(), 1);
		Date datePlus2 = DateUtils.addDays(new Date(), 2);
		Date datePlus3 = DateUtils.addDays(new Date(), 3);
		Date datePlus4 = DateUtils.addDays(new Date(), 4);
		Date datePlus5 = DateUtils.addDays(new Date(), 5);
		model.addAttribute("startDate",startDate);
		model.addAttribute("datePlus1",datePlus1);
		model.addAttribute("datePlus2",datePlus2);
		model.addAttribute("datePlus3",datePlus3);
		model.addAttribute("datePlus4",datePlus4);
		model.addAttribute("datePlus5",datePlus5);
		//Add list film to showtime
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  
		String strDate = formatter.format(startDate); 
	   
		if(datesearch == null) {
			datesearch = strDate;
			
			model.addAttribute("list",movieService.findByDate(strDate));
			
			model.addAttribute("showDateId",showdatesRepository.findbyDate(strDate));
			
		}else {
			model.addAttribute("list",movieService.findByDate(datesearch));
			
			model.addAttribute("showDateId",showdatesRepository.findbyDate(datesearch));
			
		}
		model.addAttribute("datesearch", datesearch);
		
		
		return "/ticketsellingMNG/ticketsellingMNG-showtime";
	}
	@GetMapping("/ticketsellingMNG/selectseat/{movieId}/{scheduleId}/{showDateId}")
	public String getSelectSeat(Model model,
			Authentication authentication,
			@PathVariable(name="movieId") Integer movieId,
			@PathVariable(name="scheduleId") Integer scheduleId,
			@PathVariable(name="showDateId") Integer showDateId) {
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		List<ScheduleSeat> data = scheduleSeatRepository.findAllByMovieIdAndScheduleIdAndShowDateIdOrderBySeatId(movieId,scheduleId,showDateId);
		model.addAttribute("data", data);
		ListScheduleSeatDTO list = new ListScheduleSeatDTO();
		list.setScheduleSeats(data);
		model.addAttribute("list",list);
		
		Integer cinemaRoomId = movieService.getById(movieId).getCinemaRoomId();
		CinemaRoom room = cinemaRoomRepository.findById(cinemaRoomId).orElse(null);
		if(room != null) {
			Integer numberColumn = room.getColumn();
			model.addAttribute("numberColumn",numberColumn);
		}
		
		return "/ticketsellingMNG/ticketsellingMNG-selectseat";
	}
	
	@PostMapping("/ticketsellingMNG/ticketsellingMNG-selectseat")
	public String postSelectSeat(Model model,
			Authentication authentication,
			@ModelAttribute(name="list") ListScheduleSeatDTO list,
			RedirectAttributes attribute){
		List<ScheduleSeat> listScheduleSeat = list.getScheduleSeats();
		List<ScheduleSeat> listScheduleSeatSell = new ArrayList<ScheduleSeat>();
		
		// get time
		Integer scheduleId = list.getScheduleSeats().get(0).getScheduleId();
		Schedule schedule = scheduleRepository.getById(scheduleId);
		String time = schedule.getTime();
		attribute.addFlashAttribute("time", time);
		// get film name
		Integer movieId = list.getScheduleSeats().get(0).getMovieId();
		MovieDTO movie = movieService.getById(movieId);
		String movieName = movie.getMovieNameVn();
		attribute.addFlashAttribute("movieName", movieName);
		
		//get cinema room name
		Integer cinemaRoomId = movie.getCinemaRoomId();
		CinemaRoom cinemaRoom = cinemaRoomRepository.getById(cinemaRoomId);
		String cinemaRoomName = cinemaRoom.getName();
		attribute.addFlashAttribute("cinemaRoomName", cinemaRoomName);
		//get show_date
		Integer showDateId = list.getScheduleSeats().get(0).getShowDateId();
		
		Date showDate = showdatesRepository.findDatebyId(showDateId);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		String strShowDate = formatter.format(showDate);
		attribute.addFlashAttribute("showDate", strShowDate);
		
		//filter ticket selling by status = 5
		for (ScheduleSeat scheduleSeat : listScheduleSeat) {
			if(scheduleSeat.getSeatStatus() == 5) {
				listScheduleSeatSell.add(scheduleSeat);
			}
		}
		// get Seat name
		attribute.addFlashAttribute("listScheduleSeatSell",listScheduleSeatSell);
		
		// get total money
		Integer totalMoney = 0;
		for (ScheduleSeat scheduleSeat : listScheduleSeatSell) {
			if(scheduleSeat.getSeatType() == 1) {
				totalMoney += 45000;
			}else {
				totalMoney += 60000;
			}
		}
		attribute.addFlashAttribute("totalMoney",totalMoney);
		
		return "redirect:/ticketsellingMNG/ticketsellingMNG-confirm_start";
		
	}
	@GetMapping("/ticketsellingMNG/ticketsellingMNG-confirm_start")
	public String getTicketSellingConfirm(Model model,
			Authentication authentication) {
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		 return "/ticketsellingMNG/ticketsellingMNG-confirm_start";
	}
	
}
