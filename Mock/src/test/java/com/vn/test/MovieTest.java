package com.vn.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vn.service.MovieService;

import com.vn.entity.Movie;

@SpringBootTest
public class MovieTest {
	
	@Autowired
	private MovieService movieService;

//	@Test
//	public void create_success() {
//
//		Movie movie = Movie.builder().actor("Nam").cinemaRoomId(100).content("Adventure").director("Tuan")
//				.duration(120.0f).fromDate(new Date()).toDate(new Date()).movieProductCompany("FPT Company2")
//				.version("1.0").movieNameEnglish("Ke cap giac mo").movieNameVn("Nam va nu").largeImage("abc")
//				.smallImage("dcf").build();
//
//		boolean actual = movieService.saveMovie(movie);
//		assertEquals(true, actual);
//	}

//	@Test
//	public void findAll() {
//		List<Movie> movies = movieService.getAll();
//
//		for (Movie movie : movies) {
//			System.out.println(movie.toString());
//		}
//		assertTrue(movies.size()>0);
//	}
//
//	@Test
//	public void deleteById() {
//		boolean actual = movieService.deleteById(1);
//		assertEquals(true, actual);
//	}
//
//	@Test
//	public void findById() {
//		Movie movie = movieService.getById(5);
//		assertEquals(5, movie.getId());
//		System.out.println(movie);
//	}
}
