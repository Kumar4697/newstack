package com.parul.ems.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.parul.ems.EmployeeManagementSystemApplication;
import com.parul.ems.model.Department;
import com.parul.ems.model.Employee;

/**
 * @author parul
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeManagementSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {

	private static long id=0;
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port+"/api/v1";
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testCreateEmployee() {
		Employee employee = new Employee();
		employee.setEmailId("test@gmail.com");
		employee.setFirstName("test");
		employee.setLastName("test");
		Department department = new Department();
		department.setId(1);
		employee.setDepartment(department);
		ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/employees", employee, Employee.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
		id=postResponse.getBody().getId();
	}

	@Test
	public void testGetAllEmployees() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/employees",
				HttpMethod.GET, entity, String.class);  
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetEmployeeById() {
		Employee employee = restTemplate.getForObject(getRootUrl() + "/employees/"+id, Employee.class);
		assertNotNull(employee);
		System.out.println(employee.getFirstName());
	}


	@Test
	public void testUpdateEmployee() {
		Employee employee = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Employee.class);
		employee.setFirstName("put");
		employee.setLastName("test2");
		restTemplate.put(getRootUrl() + "/employees/" + id, employee);
		Employee updatedEmployee = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Employee.class);
		assertNotNull(updatedEmployee);
	}

	@Test
	public void testDeleteEmployee() {
		Employee employee = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Employee.class);
		assertNotNull(employee);
		restTemplate.delete(getRootUrl() + "/employees/" + id);
		try {
			employee = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Employee.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
