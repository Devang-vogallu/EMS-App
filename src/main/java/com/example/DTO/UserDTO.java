package com.example.DTO;

public class UserDTO {
	 private int id;
	    private String role;
	    private String emailId;
	    private String password;
	    private EmployeeDTO employee;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public String getEmailId() {
			return emailId;
		}
		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public EmployeeDTO getEmployee() {
			return employee;
		}
		public void setEmployee(EmployeeDTO employee) {
			this.employee = employee;
		}

}
