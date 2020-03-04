package com.parul.ems.model;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author parul
 *
 */
@Entity
@Table(name = "Employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "The auto-generated version of the employee")
	private long id;

	@Column(name = "first_name", nullable = false)
	@ApiModelProperty(notes = "Employee first name")
	private String firstName;
	
	@Column(name = "last_name", nullable = false)
	@ApiModelProperty(notes = "Employee last name")
	private String lastName;
	
	@Column(name = "email_address", nullable = false)
	@ApiModelProperty(notes = "Employee email Id")
	private String emailId;

	@ManyToOne
	@JoinColumn(name = "dept_id", nullable = true)
	Department department;

	public Employee() {

	}


	public Employee(String firstName, String lastName, String emailId, Department department) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.department = department;
	}

	public Employee(String firstName, String lastName, String emailId, Set<Department> departments) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}



	public Department getDepartment() {
		return department;
	}


	public void setDepartment(Department department) {
		this.department = department;
	}


	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ "]";
	}


}
