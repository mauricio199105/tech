package com.lean.tech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lean.tech.dto.EmployeeInDTO;
import com.lean.tech.dto.PersonOutDTO;
import com.lean.tech.dto.ResponseEmployeeDTO;
import com.lean.tech.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping(value = "/create", produces = "application/json")
	public HttpStatus createEmployee(@RequestBody @Validated EmployeeInDTO employee) {

		if (this.employeeService.createEmployee(employee)) {
			return HttpStatus.OK;
		} else {
			return HttpStatus.FORBIDDEN;
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable @Validated Long id, @RequestBody EmployeeInDTO employee) {
		employee.setId(id);
		return ResponseEntity.ok().body(this.employeeService.updateEmployee(employee));
	}

	@DeleteMapping("/delete/{id}")
	public HttpStatus deleteEmployee(@PathVariable long id) {
		if (this.employeeService.deleteEmployee(id)) {
			return HttpStatus.OK;
		} else {
			return HttpStatus.FORBIDDEN;
		}

	}

	@GetMapping(value = "/get-employees", produces = "application/json")
	public List<PersonOutDTO> getEmployees(@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String postition) {
		if (!name.isEmpty() || !postition.isEmpty()) {
			return this.employeeService.getEmployeesByNameOrPosition(name, postition);
		} else {
			return this.employeeService.getAllEmployees();
		}
	}

	@GetMapping(value = "/get-positions", produces = "application/json")
	public List<ResponseEmployeeDTO> getAllPositions() {

		List<ResponseEmployeeDTO> allEmployees = this.employeeService.getAllPositions();
		return allEmployees;
	}

}
