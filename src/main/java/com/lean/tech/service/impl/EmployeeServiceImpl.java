package com.lean.tech.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lean.tech.dto.EmployeeInDTO;
import com.lean.tech.dto.EmployeeOutDTO;
import com.lean.tech.dto.PersonInDTO;
import com.lean.tech.dto.PersonOutDTO;
import com.lean.tech.dto.ResponseEmployeeDTO;
import com.lean.tech.dto.projection.EmployeeProjection;
import com.lean.tech.dto.projection.PositionProjection;
import com.lean.tech.model.Employee;
import com.lean.tech.model.Person;
import com.lean.tech.model.Position;
import com.lean.tech.repository.EmployeeRepository;
import com.lean.tech.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;

	}

	public boolean createEmployee(EmployeeInDTO employee) {
		boolean status = false;
		try {
			this.employeeRepository.save(buildEmployee(employee));
			status = true;
		} catch (IllegalArgumentException e) {
			LOG.error("An error was found", e);
		}

		return status;
	}

	public boolean updateEmployee(EmployeeInDTO employee) {
		boolean status = false;
		try {
			Optional<Employee> employeDb = this.employeeRepository.findById(employee.getId());
			if (employeDb.isPresent()) {
				employeeRepository.save(updateObject(employee, employeDb));
				status = true;
			} else {
				throw new Exception("Record not found with id : " + employee.getId());
			}
		} catch (Exception e) {
			LOG.error("An error was found updating the register", e);
		}

		return status;
	}

	public boolean deleteEmployee(long employeeId) {
		boolean status = false;
		try {
			Optional<Employee> employeeDb = this.employeeRepository.findById(employeeId);
			if (employeeDb.isPresent()) {
				this.employeeRepository.delete(employeeDb.get());
			} else {
				throw new Exception("Record not found with id : " + employeeId);
			}

		} catch (Exception e) {
			LOG.error("An error was found deleting the register", e);
		}

		return status;
	}

	public List<PersonOutDTO> getEmployeesByNameOrPosition(String name, String postition) {

		List<EmployeeProjection> employees = this.employeeRepository.findByNameOrPosition(name, postition);
		List<PersonOutDTO> persons = employees.stream().map(
				x -> new PersonOutDTO(x.getName(), x.getLastName(), x.getAdrress(), x.getCellphone(), x.getCityName()))
				.collect(Collectors.toList());

		return persons;
	}

	public List<PersonOutDTO> getAllEmployees() {
		List<Employee> employees = this.employeeRepository.findAll();

		List<PersonOutDTO> employeesPerson = employees.stream()
				.map(x -> new PersonOutDTO(x.getPerson().getName(), x.getPerson().getLastName(),
						x.getPerson().getAddress(), x.getPerson().getCellphone(), x.getPerson().getCityName()))
				.collect(Collectors.toList());

		return employeesPerson;
	}

	public List<ResponseEmployeeDTO> getAllPositions() {
		List<PositionProjection> employees = this.employeeRepository.findEmployeesByPostition();
		List<ResponseEmployeeDTO> responses = new ArrayList<>();
		List<String> positions = new ArrayList<>();
		for (PositionProjection employe : employees) {

			if (!positions.contains(employe.getnamePosition())) {
				ResponseEmployeeDTO result = new ResponseEmployeeDTO();
				result.setId(employe.getIdPosition());
				result.setName(employe.getnamePosition());

				responses.add(result);
				positions.add(employe.getnamePosition());
			}

		}

		List<ResponseEmployeeDTO> resultado = new ArrayList<>();
		for (String postition : positions) {
			List<EmployeeOutDTO> list = employees.stream()
					.filter(employee -> employee.getnamePosition().equals(postition))
					.map(x -> new EmployeeOutDTO(x.getidEmployee(), x.getsalary(), new PersonInDTO(null, x.getname(),
							x.getlastName(), x.getaddress(), x.getcellPhone(), x.getcityName())))
					.collect(Collectors.toList());

			Optional<ResponseEmployeeDTO> resultadoTemp = responses.stream().filter(name -> name.getName().equals(postition))
					.map(y -> new ResponseEmployeeDTO(y.getId(), postition, list)).findFirst();

			resultado.add(resultadoTemp.get());
		}

		return resultado;
	}

	private Employee updateObject(EmployeeInDTO employee, Optional<Employee> employeDb) {
		Employee employeeUpdate = employeDb.get();
		employeeUpdate.setSalary(employee.getSalary());

		Person personUpdate = employeeUpdate.getPerson();
		personUpdate.setName(employee.getPerson().getName());
		personUpdate.setLastName(employee.getPerson().getLastName());
		personUpdate.setAddress(employee.getPerson().getAddress());
		personUpdate.setCellphone(employee.getPerson().getCellphone());
		personUpdate.setCityName(employee.getPerson().getCityName());

		Position positionUpdate = employeeUpdate.getPosition();
		positionUpdate.setName(employee.getPosition().getName());

		employeeUpdate.setPerson(personUpdate);
		employeeUpdate.setPosition(positionUpdate);
		return employeeUpdate;
	}

	private Employee buildEmployee(EmployeeInDTO employee) {
		
		Person personEntity = Person.builder().address(employee.getPerson().getAddress())
				.cellphone(employee.getPerson().getCellphone()).name(employee.getPerson().getName())
				.lastName(employee.getPerson().getLastName()).cityName(employee.getPerson().getCityName()).build();
		Position positionEntity = Position.builder().name(employee.getPosition().getName()).build();
		Employee employeeEntity = Employee.builder().salary(employee.getSalary()).person(personEntity)
				.position(positionEntity).build();
		return employeeEntity;
	}

}
