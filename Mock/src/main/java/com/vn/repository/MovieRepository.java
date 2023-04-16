package com.vn.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vn.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	@Query("Select m from Movie m where m.movieNameEnglish like %:name%")
	Page<Movie> findByNameLike(String name, Pageable pageable);
	
	@Query("Select m from Movie m where m.movieNameEnglish like %:name%")
	List<Movie> findByNameLike(String name);
	
	@Query("Select m from Movie m where m.cinemaRoomId = :roomId")
	List<Movie> findByRoomId(@Param("roomId") Integer roomId);
	
	@Query(value = "select * from movie A left join movie_show_date B on A.movie_id = B.movie_id left join show_date C on B.show_date_id = c.show_date_id where C.show_date = :date", nativeQuery = true)
	List<Movie> findByDate(String date);

}
