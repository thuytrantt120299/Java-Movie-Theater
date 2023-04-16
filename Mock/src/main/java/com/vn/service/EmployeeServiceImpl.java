package com.vn.service;

import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vn.entity.Account;
import com.vn.entity.Role;
import com.vn.model.EmployeeDTO;
import com.vn.repository.AccountRepository;
import com.vn.repository.RoleRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private AccountRepository employeeRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Page<EmployeeDTO> getAll(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		Role roleEmployee = roleRepository.findByName("Employee");
		Page<Account> entities = employeeRepository.findByRole(roleEmployee, pageable);
		Page<EmployeeDTO> result = entities.map(new Function<Account, EmployeeDTO>() {

			@Override
			public EmployeeDTO apply(Account t) {
				EmployeeDTO dto = new EmployeeDTO();
				BeanUtils.copyProperties(t, dto);
				return dto;
			}
		});

		return result;
	}

	@Override
	public Account save(EmployeeDTO employeeDTO) {
		Role roleAccount = roleRepository.findByName("Employee");
		employeeDTO.setRole(roleAccount);
		encodePassword(employeeDTO);
		Account employee = new Account();
		BeanUtils.copyProperties(employeeDTO, employee);
		employeeRepository.save(employee);
		return employee;
	}

	@Override
	public EmployeeDTO get(Long id) {
		Account employee = employeeRepository.findById(id).orElse(null);
		EmployeeDTO dto = new EmployeeDTO();
		if (employee != null) {
			BeanUtils.copyProperties(employee, dto);
		}
		return dto;
	}

	@Override
	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}

	private void encodePassword(EmployeeDTO dto) {
		String encodedPassword = passwordEncoder.encode(dto.getPassword());
		dto.setPassword(encodedPassword);
	}

	@Override
	public Page<EmployeeDTO> searchEmployeeByName(String name, Integer page, Integer size) {
		String likeName = "%" + name + "%";
		Pageable pageable = PageRequest.of(page, size);
		Page<Account> pageEntity = employeeRepository.findByNameLike(likeName, pageable);

		Page<EmployeeDTO> result = pageEntity.map(new Function<Account, EmployeeDTO>() {

			@Override
			public EmployeeDTO apply(Account t) {
				EmployeeDTO dto = new EmployeeDTO();
				BeanUtils.copyProperties(t, dto);
				return dto;
			}
		});
		return result;
	}

}
