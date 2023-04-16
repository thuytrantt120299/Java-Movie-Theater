package com.vn.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vn.entity.ShowDate;

@Repository
public interface ShowDatesRepository extends JpaRepository<ShowDate, Integer> {
	List<ShowDate> findByDateBetween(LocalDate localFromDate, LocalDate localToDate);
	
	@Query(value="select show_date_id from Show_date where show_date =:date", nativeQuery = true)
	Integer findbyDate(String date);
	
	@Query(value="select show_date from Show_date where show_date_id =:id", nativeQuery = true)
	Date findDatebyId(Integer id);
}
