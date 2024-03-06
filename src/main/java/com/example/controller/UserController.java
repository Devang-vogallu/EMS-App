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

import com.example.DTO.UserDTO;
import com.example.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	

	@GetMapping("/login/{emailId}/{password}")
    public ResponseEntity<String> checkUserExistence(@PathVariable String emailId, @PathVariable String password) {
		ResponseEntity<String> status ;
        boolean userExists = userService.doesUserExistByEmail(emailId);

        if (userExists) {
        	
            UserDTO user = userService.findUserByEmail(emailId);
            
            
            if (user.getEmailId().equals(emailId) && user.getPassword().equals(password)) {
                status = ResponseEntity.ok("Loggin Successfull");
                return status;
            } else {
                status = ResponseEntity.ok("Incorrect Password");//status(HttpStatus.UNAUTHORIZED).body("Incorrect Password");
                return status;
            }
        } else {
            status = ResponseEntity.ok("User with emailId " + emailId + " does not exist.");//status(HttpStatus.NOT_FOUND).body("User with emailId " + emailId + " does not exist.");
            return status;
        }
    }
	
	 @PostMapping
	    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
		 if (userService.doesUserExistByEmail(userDTO.getEmailId())) {
			 return ResponseEntity.badRequest().body("Email already exists");
	        }
		 else {
	        UserDTO savedUser = userService.saveUser(userDTO);
	        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		 	}
	    }
	 
//	 UserDTO savedUser = userService.saveUser(userDTO);
//     return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	 
	 @GetMapping
	    public ResponseEntity<List<UserDTO>> getAllUsers() {
	        List<UserDTO> users = userService.getAllUsers();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    }
	 
	 @GetMapping("email/{email}")
	    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
	        UserDTO user = userService.findUserByEmail(email);
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    }
	 
	 @GetMapping("/{id}")
	    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
	        UserDTO user = userService.getUserById(id);
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    }
	 
	 
	   @PutMapping("/{id}")
	    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO updatedUserDTO) {
	        UserDTO updatedUser = userService.updateUser(id, updatedUserDTO);
	        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	    }

	 @DeleteMapping("/{id}")
	   public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
		 userService.deleteUser(id);
	       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	   }
}
