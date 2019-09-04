package com.parul.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parul.ems.model.Employee;

/**
 * @author gautam
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
