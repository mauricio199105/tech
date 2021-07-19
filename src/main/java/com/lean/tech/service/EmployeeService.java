package com.lean.tech.service;

import java.util.List;

import com.lean.tech.dto.EmployeeInDTO;
import com.lean.tech.dto.PersonOutDTO;
import com.lean.tech.dto.ResponseEmployeeDTO;

public interface EmployeeService {

	boolean createEmployee(EmployeeInDTO employee);

	boolean updateEmployee(EmployeeInDTO employee);

	boolean deleteEmployee(long employeeId);
	
	List<ResponseEmployeeDTO> getAllPositions();

	List<PersonOutDTO> getEmployeesByNameOrPosition(String name, String postition);

	List<PersonOutDTO> getAllEmployees();

}
