package com.lean.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonInDTO {

	private Long id;
	private String name;
	private String lastName;
	private String address;
	private String cellphone;
	private String cityName;

}
