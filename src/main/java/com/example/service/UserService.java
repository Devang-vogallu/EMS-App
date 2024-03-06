package com.example.service;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DTO.EmployeeDTO;
import com.example.DTO.UserDTO;
import com.example.model.Employee;
import com.example.model.User;
import com.example.repository.EmployeeRepository;
import com.example.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	
	
	public Boolean doesUserExistByEmail(String emailId) {
		User user=userRepository.findByEmailId(emailId);
        return userRepository.findByEmailId(emailId) != null;
    }
	
	public UserDTO findUserByEmail(String email) {
		User user=userRepository.findByEmailId(email);
		return convertToDTO(user);
	}
	

	
	public UserDTO getUserById(Integer id) {
	    User user = userRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
	    return convertToDTO(user);
	}
	
	
	 public List<UserDTO> getAllUsers() {
	        List<User> users = userRepository.findAll();
	        return users.stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }
	 
	  public UserDTO saveUser(UserDTO userDTO) {
	        // Convert DTOs to entities
	        User user = new User();
	        user.setRole(userDTO.getRole());
	        user.setEmailId(userDTO.getEmailId());
	        user.setPassword(userDTO.getPassword());

	        EmployeeDTO employeeDTO = userDTO.getEmployee();
	        if (employeeDTO != null) {
	            Employee employee = new Employee();
	            employee.setEmployeeName(employeeDTO.getEmployeeName());
	            employee.setEmployeeSalary(employeeDTO.getEmployeeSalary());
	            employee.setEmployeeDepartment(employeeDTO.getEmployeeDepartment());
	            employee.setEmployeeContact(employeeDTO.getEmployeeContact());
	            employee.setEmployeeLocation(employeeDTO.getEmployeeLocation());

	            // Save the employee and set it in the user
	            user.setEmployee(employeeRepository.save(employee));
	        }

	        // Save the user
	        User savedUser = userRepository.save(user);

	        // Convert the saved user entity back to DTO and return
	        return convertToDTO(savedUser);
	    }
	  
	  public void deleteUser(Integer id) {
	        User user = userRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

	        // Delete the associated employee if present
	        Employee employee = user.getEmployee();
	        if (employee != null) {
	            employeeRepository.delete(employee);
	        }

	        // Delete the user
	        userRepository.delete(user);
	    }

	  public UserDTO updateUser(Integer id, UserDTO updatedUserDTO) {
	        User existingUser = userRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

	        // Update user fields
	        existingUser.setRole(updatedUserDTO.getRole());
	        existingUser.setEmailId(updatedUserDTO.getEmailId());
	        existingUser.setPassword(updatedUserDTO.getPassword());

	        // Update associated Employee if present
	        Employee existingEmployee = existingUser.getEmployee();
	        if (updatedUserDTO.getEmployee() != null) {
	            if (existingEmployee == null) {
	                // If the user didn't have an associated employee, create a new one
	                existingEmployee = new Employee();
	                existingUser.setEmployee(existingEmployee);
	            }

	            // Update employee fields
	            EmployeeDTO updatedEmployeeDTO = updatedUserDTO.getEmployee();
	            existingEmployee.setEmployeeName(updatedEmployeeDTO.getEmployeeName());
	            existingEmployee.setEmployeeSalary(updatedEmployeeDTO.getEmployeeSalary());
	            existingEmployee.setEmployeeDepartment(updatedEmployeeDTO.getEmployeeDepartment());
	            existingEmployee.setEmployeeContact(updatedEmployeeDTO.getEmployeeContact());
	            existingEmployee.setEmployeeLocation(updatedEmployeeDTO.getEmployeeLocation());

	            // Save the updated employee
	            employeeRepository.save(existingEmployee);
	        } else if (existingEmployee != null) {
	            // If the updated DTO doesn't have an associated employee, delete the existing one
	            employeeRepository.delete(existingEmployee);
	            existingUser.setEmployee(null);
	        }

	        // Save the updated user
	        User updatedUser = userRepository.save(existingUser);

	        // Convert the updated user back to DTO and return
	        return convertToDTO(updatedUser);
	    }
	  

	    private UserDTO convertToDTO(User user) {
	        UserDTO userDTO = new UserDTO();
	        userDTO.setId(user.getId());
	        userDTO.setRole(user.getRole());
	        userDTO.setEmailId(user.getEmailId());
	        userDTO.setPassword(user.getPassword());

	        Employee employee = user.getEmployee();
	        if (employee != null) {
	            EmployeeDTO employeeDTO = new EmployeeDTO();
	            employeeDTO.setEmployeeId(employee.getEmployeeId());
	            employeeDTO.setEmployeeName(employee.getEmployeeName());
	            employeeDTO.setEmployeeSalary(employee.getEmployeeSalary());
	            employeeDTO.setEmployeeDepartment(employee.getEmployeeDepartment());
	            employeeDTO.setEmployeeContact(employee.getEmployeeContact());
	            employeeDTO.setEmployeeLocation(employee.getEmployeeLocation());

	            userDTO.setEmployee(employeeDTO);
	        }

	        return userDTO;
	    }
	
}
	
	
