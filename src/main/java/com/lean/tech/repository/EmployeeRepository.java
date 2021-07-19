package com.lean.tech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lean.tech.constans.ConstantsSql;
import com.lean.tech.dto.projection.EmployeeProjection;
import com.lean.tech.dto.projection.PositionProjection;
import com.lean.tech.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query(value = ConstantsSql.FIND_EMPLOYEE_BY_POSITION, nativeQuery = true)
	List<PositionProjection> findEmployeesByPostition();

	@Query(value = ConstantsSql.FIND_BY_NAME_OR_POSITION, nativeQuery = true)
	List<EmployeeProjection> findByNameOrPosition(@Param("name") String name, @Param("position") String position);

}
