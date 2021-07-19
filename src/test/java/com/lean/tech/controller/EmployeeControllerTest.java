package com.lean.tech.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lean.tech.dto.EmployeeInDTO;
import com.lean.tech.dto.PersonInDTO;
import com.lean.tech.dto.PositionDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mock;

	@Test
	public void createEmployeeTest() throws Exception {

		PersonInDTO person = PersonInDTO.builder().name("Mauricio").lastName("Mejia").address("Cra 65")
				.cellphone("3136588299").cityName("Medellin").build();
		PositionDTO position = PositionDTO.builder().id(1L).name("DEV").build();

		EmployeeInDTO employee = EmployeeInDTO.builder().id(2L).person(person).position(position).build();

		this.mock.perform(post("/employee/create").contentType(APPLICATION_JSON_UTF8).content(asJsonString(employee)))
				.andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
