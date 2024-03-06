package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.model.Employee;
import com.example.service.EmployeeService;

@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("")
	public ResponseEntity<String>addEmployee(@RequestBody Employee employee)
	{
		@SuppressWarnings("unused")
		Employee employee1 = employeeService.addEmployee(employee);
		
		return new ResponseEntity<String>("Record Added Successfully", HttpStatus.CREATED);
	}
	@GetMapping("")
	public ResponseEntity<List<Employee>> getAllEmployee()
	{
		@SuppressWarnings("unused")
		List<Employee> employeeList =  employeeService.getAllEmployee();
		return new ResponseEntity<List<Employee>>(employeeService.getAllEmployee(),HttpStatus.OK);
	}
	
	 @GetMapping("/{id}")
	    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
	        Employee employee = employeeService.getEmployeeById(id);
	        return new ResponseEntity<>(employee, HttpStatus.OK);
	    }
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee updatedEmployee) {
	        Employee updated = employeeService.updateEmployee(id, updatedEmployee);
	        return new ResponseEntity<>(updated, HttpStatus.OK);
	    }

	 @DeleteMapping("/{id}")
	   public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
	       employeeService.deleteEmployee(id);
	       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	   }
	
}
