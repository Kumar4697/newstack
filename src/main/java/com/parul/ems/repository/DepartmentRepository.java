package com.parul.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parul.ems.model.Department;

/**
 * @author gautam
 *
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
