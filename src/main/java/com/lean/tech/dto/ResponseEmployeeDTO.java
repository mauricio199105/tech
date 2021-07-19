package com.lean.tech.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseEmployeeDTO {

	private Long id;
	private String name;
	private List<EmployeeOutDTO> employess;

}
