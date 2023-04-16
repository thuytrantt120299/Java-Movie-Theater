package com.vn.test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.vn.entity.Schedule;
import com.vn.repository.ScheduleRepository;

@SpringBootTest
public class ScheduleTest {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Test
	public void addSchedule() {
		
		List<Schedule> schedules = Arrays.asList(
				new Schedule(1,"08:00"),
				new Schedule(2,"09:00"),
				new Schedule(3,"10:00"),
				new Schedule(4,"11:00"),
				new Schedule(5,"13:30"),
				new Schedule(6,"14:30"),
				new Schedule(7,"15:00"),
				new Schedule(8,"17:00"),
				new Schedule(9,"18:30"),
				new Schedule(10,"19:00"),
				new Schedule(11,"20:00"),
				new Schedule(12,"21:00")
				);
		
		scheduleRepository.saveAll(schedules);
	
		assertThat(schedules.size()).isEqualTo(12);
		

	
	}
}
