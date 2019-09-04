package com.parul.ems.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.parul.ems.exception.ResourceNotFoundException;
import com.parul.ems.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parul.ems.model.Department;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author parul
 *
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins="*")
@Api(value="Employee", description="Operations pertaining to Department")
public class DepartmentController {

	@Autowired
	private DepartmentRepository departmentRepository;

	/**
	 * @return List<Department>
	 */
	@ApiOperation(value = "View a list of departments", response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved department detail"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
			)
	@GetMapping("/departments")
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	/**
	 * @param DepartmentId
	 * @return Department
	 * @throws ResourceNotFoundException
	 */
	@ApiOperation(value = "View a department detail by departmentId", response = Department.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved department detail"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
			)
	@GetMapping("/departments/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable(value = "id") Long departmentId)
			throws ResourceNotFoundException {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found for this id :: " + departmentId));
		return ResponseEntity.ok().body(department);
	}

	/**
	 * @param Department
	 * @return Department
	 */
	@ApiOperation(value = "Save a department", response = Department.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully saved a department detail"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
			)
	@PostMapping("/departments")
	public Department createDepartment(@Valid @RequestBody Department department) {
		return departmentRepository.save(department);
	}

	/**
	 * @param DepartmentId
	 * @param DepartmentDetails
	 * @return Department
	 * @throws ResourceNotFoundException
	 */
	@ApiOperation(value = "Update a department detail by departmentId", response = Department.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated department detail"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
			)
	@PutMapping("/departments/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable(value = "id") Long departmentId,
			@Valid @RequestBody Department departmentDetails) throws ResourceNotFoundException {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found for this id :: " + departmentId));

		department.setId(departmentDetails.getId());
		department.setName(departmentDetails.getName());
		department.setEmployees(departmentDetails.getEmployees());
		Department updatedDepartment = departmentRepository.save(departmentDetails);
		return ResponseEntity.ok(updatedDepartment);
	}

	/**
	 * @param DepartmentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@ApiOperation(value = "Delete a department detail by departmentId", response = Department.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully deleted department detail"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
			)
	@DeleteMapping("/departments/{id}")
	public Map<String, Boolean> deleteDepartment(@PathVariable(value = "id") Long departmentId)
			throws ResourceNotFoundException {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found for this id :: " + departmentId));

		departmentRepository.delete(department);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	/**
	 * @param departmentId
	 * @param departmentDetails
	 * @return Department
	 * @throws ResourceNotFoundException
	 */
	@ApiOperation(value = "Patch a department detail by departmentId", response = Department.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully patched department detail"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
			)
	@PatchMapping("/departments/{id}")
	public ResponseEntity<Department> patchDepartment(@PathVariable(value = "id") Long departmentId,
			@Valid @RequestBody Department departmentDetails) throws ResourceNotFoundException {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found for this id :: " + departmentId));

		if(null != departmentDetails.getName()) {
			department.setName(departmentDetails.getName());
		}
		if(null != departmentDetails.getEmployees()) {
			department.setEmployees(departmentDetails.getEmployees());
		}
		Department updatedDepartment = departmentRepository.save(department);
		return ResponseEntity.ok(updatedDepartment);
	}
}
