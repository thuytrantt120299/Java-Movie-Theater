package com.vn.model;

import java.util.ArrayList;
import java.util.List;

import com.vn.entity.Seat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Listseatdto{
	
	private List<Seat> seats = new ArrayList<Seat>();
	
	public void addSeat(Seat seat) {
		this.seats.add(seat);
	}
	
}
