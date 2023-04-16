package com.vn.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "SCHEDULE_SEAT")
public class ScheduleSeat {
	
	@Id
	@Column(name = "SCHEDULE_SEAT_ID")
	private String id;
	
	@Column(name = "MOVIE_ID", length = 255)
	private Integer movieId;
	
	@Column(name = "SCHEDULE_ID", length = 10)
	private Integer scheduleId;
	
	@Column(name = "SHOW_DATE_ID", length = 10)
	private Integer showDateId;
	
	@Column(name = "SEAT_ID", length = 10)
	private Integer seatId;
	
	@Column(name = "SEAT_COLUMN", length = 255)
	private String seatColumn;
	
	@Column(name = "SEAT_ROW", length = 10)
	private Integer seatRow;
	
	@Column(name = "SEAT_STATUS", length = 1)
	private Integer seatStatus;
	
	@Column(name = "SEAT_TYPE", length = 1)
	private Integer seatType;

	@Override
	public int hashCode() {
		return Objects.hash(id, movieId, scheduleId, seatColumn, seatId, seatRow, seatStatus, seatType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleSeat other = (ScheduleSeat) obj;
		return Objects.equals(id, other.id) && Objects.equals(movieId, other.movieId)
				&& Objects.equals(scheduleId, other.scheduleId) && Objects.equals(seatColumn, other.seatColumn)
				&& Objects.equals(seatId, other.seatId) && Objects.equals(seatRow, other.seatRow)
				&& Objects.equals(seatStatus, other.seatStatus) && Objects.equals(seatType, other.seatType);
	}
	
}
