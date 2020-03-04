

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

/**
 * @author parul
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeManagementSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentControllerIntegrationTest {

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
	public void testCreateDepartment() {
		Department department = new Department();
		department.setName("name");
		ResponseEntity<Department> postResponse = restTemplate.postForEntity(getRootUrl() + "/departments", department, Department.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
		id=postResponse.getBody().getId();
	}

	@Test
	public void testGetAllDepartment() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/departments",
				HttpMethod.GET, entity, String.class);  
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetDepartmentById() {
		Department department = restTemplate.getForObject(getRootUrl() + "/departments/"+id, Department.class);
		assertNotNull(department);
		System.out.println(department.getName());
	}


	@Test
	public void testUpdateDepartment() {
		Department department = restTemplate.getForObject(getRootUrl() + "/departments/" + id, Department.class);
		department.setName("name new");
		restTemplate.put(getRootUrl() + "/departments/" + id, department);
		Department updatedDepartment = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Department.class);
		assertNotNull(updatedDepartment);
	}

	@Test
	public void testDeleteDepartment() {
		Department department = restTemplate.getForObject(getRootUrl() + "/departments/" + id, Department.class);
		assertNotNull(department);
		restTemplate.delete(getRootUrl() + "/departments/" + id);
		try {
			department = restTemplate.getForObject(getRootUrl() + "/departments/" + id, Department.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
