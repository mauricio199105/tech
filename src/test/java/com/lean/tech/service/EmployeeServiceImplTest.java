package com.lean.tech.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import com.lean.tech.dto.EmployeeInDTO;
import com.lean.tech.dto.PersonInDTO;
import com.lean.tech.dto.PositionDTO;
import com.lean.tech.model.Employee;
import com.lean.tech.model.Person;
import com.lean.tech.model.Position;
import com.lean.tech.repository.EmployeeRepository;
import com.lean.tech.service.impl.EmployeeServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeServiceImplTest {

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Mock
	private EmployeeRepository employeeRepository;

	@BeforeTestClass
	public void setUp() {
		employeeService = new EmployeeServiceImpl(employeeRepository);
	}

	@Test
	public void createEmployeeTest() {

		PersonInDTO person = PersonInDTO.builder().name("Mauricio").lastName("Mejia").address("Cra 65")
				.cellphone("3136588299").cityName("Medellin").build();
		PositionDTO position = PositionDTO.builder().id(1L).name("DEV").build();

		EmployeeInDTO employee = EmployeeInDTO.builder().id(2L).person(person).position(position).build();

		boolean result = this.employeeService.createEmployee(employee);

		assertTrue(result);

	}

	@Test
	public void createEmployeeWithExceptionTest() {

		PersonInDTO person = PersonInDTO.builder().name("Mauricio").lastName("Mejia").address("Cra 65")
				.cellphone("3136588299").cityName("Medellin").build();
		PositionDTO position = PositionDTO.builder().id(1L).name("DEV").build();

		EmployeeInDTO employee = EmployeeInDTO.builder().id(2L).person(person).position(position).build();

		doThrow(IllegalArgumentException.class).when(this.employeeRepository).save(Mockito.any(Employee.class));

		boolean result = this.employeeService.createEmployee(employee);

		assertFalse(result);

	}

	private Employee buildEmployee() {

		Person personEntity = Person.builder().address("Cra 56").cellphone("3136588299").name("Mauricio")
				.lastName("Mejia").cityName("Medellin").build();
		Position positionEntity = Position.builder().name("DEV").build();
		Employee employeeEntity = Employee.builder().salary(BigDecimal.valueOf(10000000)).person(personEntity)
				.position(positionEntity).build();
		return employeeEntity;
	}

}
