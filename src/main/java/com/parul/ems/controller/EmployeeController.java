package com.parul.ems.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.parul.ems.exception.ResourceNotFoundException;
import com.parul.ems.model.Employee;
import com.parul.ems.repository.EmployeeRepository;

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
@Api(value="Employee", description="Operations pertaining to Employees")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * @return List<Employee>
	 */
	@ApiOperation(value = "View a list of employees", response = Iterable.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list of employees"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	/**
	 * @param employeeId
	 * @return Employee
	 * @throws ResourceNotFoundException
	 */
	@ApiOperation(value = "View employee by employeeId", response = Employee.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved employee detail"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	/**
	 * @param employee
	 * @return Employee
	 */
	@ApiOperation(value = "Save an employee", response = Employee.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Successfully saved employee detail"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@PostMapping("/employees")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	/**
	 * @param employeeId
	 * @param employeeDetails
	 * @return Employee
	 * @throws ResourceNotFoundException
	 */
	@ApiOperation(value = "Update employee by employeeId", response = Employee.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully updated employee detail"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setDepartment(employeeDetails.getDepartment());
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	/**
	 * @param employeeId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@ApiOperation(value = "Delete employee by employeeId", response = Employee.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully deleted employee detail"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	/**
	 * @param employeeId
	 * @param employeeDetails
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@ApiOperation(value = "Patch employee by employeeId", response = Employee.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully patched employee detail"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@PatchMapping("/employees/{id}")
	public ResponseEntity<Employee> patchEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		if(null != employeeDetails.getEmailId()) {
			employee.setEmailId(employeeDetails.getEmailId());
		}
		if(null != employeeDetails.getLastName()) {
			employee.setLastName(employeeDetails.getLastName());
		}
		if(null != employeeDetails.getFirstName()) {
			employee.setFirstName(employeeDetails.getFirstName());
		}
		if(null != employeeDetails.getDepartment()) {
			employee.setDepartment(employeeDetails.getDepartment());
		}
		
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
}
