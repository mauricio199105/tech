package com.lean.tech.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeOutDTO {

	private Long id;
	private BigDecimal salary;
	private PersonInDTO person;

}
