package com.vn.service;

import org.springframework.data.domain.Page;

import com.vn.entity.Account;
import com.vn.model.EmployeeDTO;

public interface EmployeeService {
	
	public Page<EmployeeDTO> getAll(Integer page, Integer size);
	
	public Account save(EmployeeDTO employeeDTO);
	
	public EmployeeDTO get(Long id);
	
	public void delete(Long id);
	
	public Page<EmployeeDTO> searchEmployeeByName(String name, Integer page, Integer size);
	
}
