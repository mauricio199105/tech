package com.lean.tech.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Employee  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private BigDecimal salary;

	@JoinColumn(name="id_person",unique=true)
	@OneToOne(cascade=CascadeType.ALL)
	private Person person;
	@JoinColumn(name="id_position",unique=true)
	@OneToOne(cascade=CascadeType.ALL)
	@OrderBy("name")
	private Position position;

}
