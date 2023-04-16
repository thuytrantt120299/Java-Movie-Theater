package com.vn.model;

import java.util.List;

import com.vn.entity.Invoice;
import com.vn.entity.ScheduleSeat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListScheduleSeatInvoiceDTO {
	List<ScheduleSeat> scheduleSeats;
	Invoice invoice;
	String cinemaRoomName;
	String showDate;
	public void addScheduleSeat(ScheduleSeat scheduleseat) {
		this.scheduleSeats.add(scheduleseat);
	}
	
}
