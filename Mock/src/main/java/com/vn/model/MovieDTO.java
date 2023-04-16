package com.vn.model;

import java.util.List;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.vn.entity.Movie;
import com.vn.entity.Schedule;
import com.vn.entity.ShowDate;
import com.vn.entity.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
	
	private Integer id;

//	@NotEmpty(message = "{movie.type.notempty}")
	private List<Type> types;

//	@NotEmpty(message = "{movie.schedule.notempty}")
	private List<Schedule> schedules;

	private List<ShowDate> showDates;

	private String actor;

	private Integer cinemaRoomId;


	private String content;

	private String director;

//	@NotNull(message = "{movie.duration.notnull}")
	private Integer duration;

	private String fromDate;

	private String movieProductCompany;

	private String toDate;


	private String version;
	
//	@NotBlank(message = "{movie.movieNameEnglish.notblank}")
	private String movieNameEnglish;

	/* @NotBlank(message = "{movie.movieNameVn.notblank}") */
	private String movieNameVn;

	private String largeImage;

	private String smallImage;
}
