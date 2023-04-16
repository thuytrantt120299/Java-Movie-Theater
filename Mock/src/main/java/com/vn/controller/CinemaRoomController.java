package com.vn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vn.entity.Account;
import com.vn.entity.CinemaRoom;
import com.vn.entity.Seat;
import com.vn.model.CinemaRoomDTO;
import com.vn.model.Listseatdto;
import com.vn.repository.SeatRepository;
import com.vn.service.AccountService;
import com.vn.service.CinemaRoomService;
import com.vn.service.auth.CustomAccountDetails;

@Controller
public class CinemaRoomController {
	@Autowired
	private CinemaRoomService cinemaRoomService;
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private AccountService accountService;
	
	//Show list room
	@GetMapping(value= {"/roomMNG/roomMNG-list"})
	public String showListCinemaRoom(Model model,
			Authentication authentication,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value="page", required = false, defaultValue = "1") Integer page,
			@RequestParam(value="size", required = false, defaultValue = "3") Integer size
			) {
		
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		if(name == null || "".equals(name)) {
			name = "";
			model.addAttribute("list",cinemaRoomService.getAll(page-1,size));
		}else {
			model.addAttribute("list",cinemaRoomService.searchCinemaRoombyName(name,page-1,size));			
		}
		model.addAttribute("name", name);
		return "/roomMNG/roomMNG-list";
	}
	
	//Add new room
	@GetMapping (value= {"/roomMNG/roomMNG-add"})
	public String getCinemaRoom(Model model, Authentication authentication) {
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		
		CinemaRoomDTO cinemaRoomDTO = new CinemaRoomDTO();
		model.addAttribute("dto",cinemaRoomDTO);
		
		return "/roomMNG/roomMNG-add";
	}
	@PostMapping (value= {"/roomMNG/roomMNG-add"})
	public String postCinemaRoom(@ModelAttribute(name = "dto") CinemaRoomDTO cinemaRoomDTO) {
		CinemaRoom cinemaRoom = cinemaRoomService.saveCinemaRoom(cinemaRoomDTO);
		
		Integer numberRow = cinemaRoomDTO.getRow();
		Integer numberColumn = cinemaRoomDTO.getColumn();
		
		for(int i = 0; i<numberRow; i++) {
			for(int j = 0; j<numberColumn; j++) {
				Seat seat = new Seat();
				char charSeatColumn = (char) (j + 65);
				String seatColumn = String.valueOf(charSeatColumn);
				seat.setRow(i+1);
				seat.setColumn(seatColumn);
				seat.setStatus(1);
				seat.setType(1);
				seat.setCinemaRoomId(cinemaRoom);
				seatRepository.save(seat);
			}
		}
		
		return "redirect:/roomMNG/roomMNG-list";
	}
	
	//Seat detail
	@GetMapping(value={"/roomMNG/roomMNG-seatdetail/{roomId}"})
	public String getSeatDetail(Model model,
			Authentication authentication,
			@PathVariable(value = "roomId") Integer roomId) {
		
		if(authentication !=null) {
			CustomAccountDetails accountlogin = (CustomAccountDetails) authentication.getPrincipal();
			Account account = accountService.findByUsername(accountlogin.getUsername());
			model.addAttribute("account", account);
		}
		
		//Add List<seat> in model
		CinemaRoomDTO room = cinemaRoomService.findById(roomId);
		String roomName = room.getName();
		Integer numberColumn = room.getColumn();
		List<Seat> data = room.getSeats();
		model.addAttribute("seats",data);
		model.addAttribute("roomName",roomName);
		model.addAttribute("numberColumn",numberColumn);
		//Add Wrapper object in model
		Listseatdto list = new Listseatdto();
		list.setSeats(data);
		
		model.addAttribute("list",list);
		return "/roomMNG/roomMNG-seatdetail";
	}
	
	@PostMapping(value= {"/roomMNG/roomMNG-seatdetail"})
	public String postSeatDetail(Model model,
			Authentication authentication,
			@ModelAttribute(name="list") Listseatdto list
			) {
			List<Seat> listSeat = list.getSeats();
			Integer seatId = listSeat.get(0).getId();
			CinemaRoom cinemaRoomId = seatRepository.getById(seatId).getCinemaRoomId();
			System.out.println(list.getSeats().size());
			for (Seat seat : listSeat) {
				seat.setCinemaRoomId(cinemaRoomId);
				System.out.println(seat.toString());
				System.out.println(seat.getCinemaRoomId().getId());
				seatRepository.save(seat);
			}
		return "redirect:/roomMNG/roomMNG-list";
	}
	
}
