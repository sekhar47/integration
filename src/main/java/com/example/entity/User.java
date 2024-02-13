package com.example.entity;


import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Employee", uniqueConstraints = @UniqueConstraint(columnNames = "empemail"))
@Entity
public class User {
	

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String empid;
	private String name;
	private String empemail; 
	private String password;
	private String empmobile;
	private Boolean availability;
	private String privilage;
	

    @Column(name = "designation")
	private String designation;

	private String token;
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime tokenCreationDate;
	
	@Lob
	@Column(name = "profile_picture", columnDefinition="LONGBLOB")
	private byte[] profilePicture;


	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<EmployeeSkill> employeeSkills;
	
	
	public Set<EmployeeSkill> getEmployeeSkills() {
		return employeeSkills;
	}

	public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
		this.employeeSkills = employeeSkills;
	}

	// Getter and setter methods for the "designation" field
	public String getDesignation() {
	    return designation;
	}

	public void setDesignation(String designation) {
	    this.designation = designation;
	}
	
	public byte[] getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmpemail() {
		return empemail;
	}
	public void setEmpemail(String empemail) {
		this.empemail = empemail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmpmobile() {
		return empmobile;
	}
	public void setEmpmobile(String empmobile) {
		this.empmobile = empmobile;
	}


	public Boolean getAvailability() {
	    // Check if availability is null, return false if it is
	    return availability != null ? availability : false;
	}
	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
	public String getPrivilage() {
		return privilage;
	}
	public void setPrivilage(String privilage) {
		this.privilage = privilage;
	}
	
	
	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getTokenCreationDate() {
		return tokenCreationDate;
	}

	public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
		this.tokenCreationDate = tokenCreationDate;
	}

	
	public User(String empid, String name, String empemail, String password, String empmobile, Boolean availability,
			String privilage, String designation, String token, LocalDateTime tokenCreationDate, byte[] profilePicture,
			Set<EmployeeSkill> employeeSkills) {
		super();
		this.empid = empid;
		this.name = name;
		this.empemail = empemail;
		this.password = password;
		this.empmobile = empmobile;
		this.availability = availability;
		this.privilage = privilage;
		this.designation = designation;
		this.token = token;
		this.tokenCreationDate = tokenCreationDate;
		this.profilePicture = profilePicture;
		this.employeeSkills = employeeSkills;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	


	

}
