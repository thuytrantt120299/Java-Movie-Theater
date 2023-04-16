package com.vn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vn.entity.Movie;
import com.vn.entity.ShowDate;
import com.vn.model.MovieDTO;
import com.vn.repository.MovieRepository;
import com.vn.repository.ShowDatesRepository;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ShowDatesRepository showDatesRepository;

	@Override
	public Page<MovieDTO> getAll(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("fromDate"));
		Page<Movie> entities = movieRepository.findAll(pageable);
		Page<MovieDTO> result = entities.map(new Function<Movie, MovieDTO>() {

			@Override
			public MovieDTO apply(Movie t) {
				MovieDTO dto = new MovieDTO();
				BeanUtils.copyProperties(t, dto);
				return dto;
			}
		});

		return result;
	}

	@Override
	public Page<MovieDTO> searchMovieByName(String name, Integer page, Integer size) {
		String likeNameEng = "%" + name + "%";
		Pageable pageable = PageRequest.of(page, size, Sort.by("fromDate"));
		Page<Movie> pageEntity = movieRepository.findByNameLike(likeNameEng, pageable);

		Page<MovieDTO> result = pageEntity.map(new Function<Movie, MovieDTO>() {

			public MovieDTO apply(Movie t) {
				MovieDTO dto = new MovieDTO();
				BeanUtils.copyProperties(t, dto);
				return dto;
			}

		});
		return result;
	}

//	private boolean isImageFile(MultipartFile file) {
//		// Let's install FileNameUtils
//		String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
//		return Arrays.asList(new String[] { "png", "jpg", "jpeg", "bmp" }).contains(fileExtension.trim().toLowerCase());
//	}

	@Override
	public Movie saveMovie(MovieDTO dto) throws ParseException {
		boolean flag = true;

		Movie movie = new Movie();

		List<Movie> movieFindByName = movieRepository.findByNameLike(dto.getMovieNameEnglish());
		for (Movie movie2 : movieFindByName) {

			// Check for add same Movie and same room
			if (dto.getCinemaRoomId().equals(movie2.getCinemaRoomId())) {
				flag = false;
				throw new RuntimeException(
						"Failed to add movie because the movie already add in this the room. Please change cinema room ");
			}

		}

		// Check for add same room and same day, add when the day end

		List<Movie> movieFindByRoomId = movieRepository.findByRoomId(dto.getCinemaRoomId());
		for (Movie movie3 : movieFindByRoomId) {
			if ((dto.getFromDate().compareTo(movie3.getToDate()) > 0)
					|| (dto.getToDate().compareTo(movie3.getFromDate()) < 0))  {
				flag = true;
			} else {
				throw new RuntimeException("Failed to add movie because date in this rooom is already used ");
			}
		}

		/*
		 * Compare from date always < to date, The day start a movie always before the
		 * end
		 */

		if (dto.getToDate().compareTo(dto.getFromDate()) < 0) {
			flag = false;
			throw new RuntimeException("The day start a movie always before the end ");
		}

		movie.setId(dto.getId());
		movie.setActor(dto.getActor());
		movie.setCinemaRoomId(dto.getCinemaRoomId());
		movie.setContent(dto.getContent());
		movie.setDirector(dto.getDirector());
		movie.setFromDate(dto.getFromDate());
		movie.setDuration(dto.getDuration());
		movie.setToDate(dto.getToDate());
		movie.setVersion(dto.getVersion());
		movie.setMovieNameEnglish(dto.getMovieNameEnglish());
		movie.setMovieNameVn(dto.getMovieNameVn());
		movie.setMovieProductCompany(dto.getMovieProductCompany());
		movie.setLargeImage(dto.getLargeImage());
		movie.setSmallImage(dto.getSmallImage());
		movie.setTypes(dto.getTypes());
		movie.setSchedules(dto.getSchedules());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localFromDate = LocalDate.parse(dto.getFromDate(), formatter);
		LocalDate localToDate = LocalDate.parse(dto.getToDate(), formatter);
		
		List<ShowDate> listShowDate = showDatesRepository.findByDateBetween(localFromDate, localToDate);
		movie.setShowDates(listShowDate);

		/*
		 * Parse from date, to date to ShowDate
		 */

//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDate localFromDate = LocalDate.parse(dto.getFromDate(), formatter);
//		LocalDate localToDate = LocalDate.parse(dto.getToDate(), formatter);
//		Period period = Period.between(localFromDate, localToDate);
//		int days = period.getDays();
//		ShowDate showDate = new ShowDate();
//		List<Movie> list =  
//		
//		for(int i = 0; i <= days ; i++) {
//			showDate.setId(i);
//			LocalDate nextDate = localFromDate.plusDays(i);
//			showDate.setMovies(dto.getId());
//			showDate.setDate(nextDate); 
//		}
//		showDatesRepository.save(showDate);
//		
		if (flag) {
			movieRepository.save(movie);
		} else {
			return null;
		}
		
		return movie;
	}

	@Override
	public Movie updateMovie(MovieDTO dto) {
		Movie movie = new Movie();
//		List<Movie> movieTest = movieRepository.findByNameLike(dto.getMovieNameEnglish());
//		for (Movie movie2 : movieTest) {
// 		Check for add same Schedule and same room
//			if ((dto.getSchedules().equals(movie2.getSchedules()))
//					&& (dto.getCinemaRoomId().equals(movie2.getCinemaRoomId()))) {
//				throw new RuntimeException("Failed to add movie because schedule in this rooom is already used ");
//			}

//		}
		/*
		 * Compare from date always < to date, The day start a movie always before the
		 * end
		 */

		if (dto.getToDate().compareTo(dto.getFromDate()) < 0) {
			throw new RuntimeException("The day start a movie always before the end ");
		}

		movie.setId(dto.getId());
		movie.setActor(dto.getActor());
		movie.setCinemaRoomId(dto.getCinemaRoomId());
		movie.setContent(dto.getContent());
		movie.setDirector(dto.getDirector());
		movie.setFromDate(dto.getFromDate());
		movie.setDuration(dto.getDuration());
		movie.setToDate(dto.getToDate());
		movie.setVersion(dto.getVersion());
		movie.setMovieNameEnglish(dto.getMovieNameEnglish());
		movie.setMovieNameVn(dto.getMovieNameVn());
		movie.setMovieProductCompany(dto.getMovieProductCompany());
		movie.setLargeImage(dto.getLargeImage());
		movie.setSmallImage(dto.getSmallImage());
		movie.setTypes(dto.getTypes());
		movie.setSchedules(dto.getSchedules());
		movie.setShowDates(dto.getShowDates());
		movieRepository.save(movie);
		return movie;
	}
	

	@Override
	public MovieDTO getById(Integer id) {
		Movie movie = movieRepository.findById(id).orElse(null);

		MovieDTO dto = new MovieDTO();
		if (movie != null) {
			BeanUtils.copyProperties(movie, dto);
		}
		return dto;
	}

	@Override
	public void deleteById(Integer id) {
		movieRepository.deleteById(id);

	}

	@Override
	public List<MovieDTO> seachMovie(String name) {
		String likeNameEng = "%" + name + "%";
		List<Movie> movies = movieRepository.findByNameLike(likeNameEng);

		List<MovieDTO> result = new ArrayList<MovieDTO>();

		if (movies != null) {
			BeanUtils.copyProperties(movies, result);
		}

		return result;
	}

	@Override
	public List<MovieDTO> findByDate(String date) {
		List<Movie> movies = movieRepository.findByDate(date);
		
		List<MovieDTO> result = new ArrayList<MovieDTO>();
		for (Movie movie : movies) {
			MovieDTO movieDTO = new MovieDTO();
			movieDTO.setActor(movie.getActor());
			movieDTO.setCinemaRoomId(movie.getCinemaRoomId());
			movieDTO.setContent(movie.getContent());
			movieDTO.setDirector(movie.getDirector());
			movieDTO.setDuration(movie.getDuration());
			movieDTO.setFromDate(movie.getFromDate());
			movieDTO.setId(movie.getId());
			movieDTO.setLargeImage(movie.getLargeImage());
			movieDTO.setMovieNameEnglish(movie.getMovieNameEnglish());
			movieDTO.setMovieNameVn(movie.getMovieNameVn());
			movieDTO.setMovieProductCompany(movie.getMovieProductCompany());
			movieDTO.setSchedules(movie.getSchedules());
			movieDTO.setShowDates(movie.getShowDates());
			movieDTO.setSmallImage(movie.getSmallImage());
			movieDTO.setToDate(movie.getToDate());
			movieDTO.setTypes(movie.getTypes());
			movieDTO.setVersion(movie.getVersion());
			result.add(movieDTO);
		}
		
		return result;
	}

}
