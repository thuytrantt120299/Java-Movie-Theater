package com.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vn.entity.ScheduleSeat;

@Repository
public interface ScheduleSeatRepository extends JpaRepository<ScheduleSeat, Integer> {

	List<ScheduleSeat> findAllByMovieIdAndScheduleIdAndShowDateIdOrderBySeatId(Integer movieId, Integer scheduleId,
			Integer showDateId);
	@Query(value="select * from schedule_seat where schedule_seat_id =:id", nativeQuery = true)
	ScheduleSeat querybyId(Integer id);


}
