package com.vn.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.vn.entity.Movie;
import com.vn.model.MovieDTO;

public interface MovieService {
	 Movie saveMovie(MovieDTO dto) throws ParseException;
	 
	 Movie updateMovie(MovieDTO dto);
	 
	 MovieDTO getById(Integer id);
	 
	 void deleteById(Integer id);
	 
	 Page<MovieDTO> getAll(Integer page, Integer size);
	 
	 Page<MovieDTO> searchMovieByName(String  name, Integer page, Integer size);
	 
	 List<MovieDTO> seachMovie(String name);
	 
	 List<MovieDTO> findByDate(String date);

}
