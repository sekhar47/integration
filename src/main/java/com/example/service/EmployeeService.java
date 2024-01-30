package com.example.service;

import com.example.dto.EmployeeDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    List<EmployeeDetailsDTO> getAllEmployeeDetails();
    
//    sesrch and filter module

    List<EmployeeDetailsDTO> findByEmpid(String empid);
//
//    List<EmployeeDetailsDTO> findBySkillname(String skillname);
//
//    List<EmployeeDetailsDTO> findByDomain(String domain);
//
//    List<EmployeeDetailsDTO> findBySubdomain(String subdomain);
//
//    List<EmployeeDetailsDTO> findByProficiency(String proficiency);

//    filter
    List<EmployeeDetailsDTO> searchEmployees(String empid, String skillname, String domain, String subdomain, String proficiency);

//profile generate 
}

