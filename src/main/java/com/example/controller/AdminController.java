package com.example.controller;


import com.example.dto.EmployeeDetailsDTO;
import com.example.service.EmployeeService;
import com.opencsv.CSVWriter;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EmployeeService adminService;

    @Autowired
    public AdminController(EmployeeService adminService) {
        this.adminService = adminService;
    }

    
    @GetMapping("/employeeDetails")
    @PreAuthorize("hasRole('ADMIN')")
    public List<EmployeeDetailsDTO> getAllEmployeeDetails() {
        return adminService.getAllEmployeeDetails();
    }
    
    
    @GetMapping("/admin/employees")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEmployeeDetails(Model model) {
        List<EmployeeDetailsDTO> employees = adminService.getAllEmployeeDetails();
        model.addAttribute("employees", employees);
        return "employees"; // This corresponds to the filename of your HTML without the extension
    }
//    
    

    
//    search and filters
//    @GetMapping("/searchByEmpid/{empid}")
//    public String searchByEmpid(@PathVariable String empid, Model model) {
//        List<EmployeeDetailsDTO> employees = adminService.findByEmpid(empid);
//        model.addAttribute("employees", employees);
//        return "employees";
//    }
//
//    @GetMapping("/searchBySkillname/{skillname}")
//    public String searchBySkillname(@PathVariable String skillname, Model model) {
//        List<EmployeeDetailsDTO> employees = adminService.findBySkillname(skillname);
//        model.addAttribute("employees", employees);
//        return "employees";
//    }
//
//    @GetMapping("/searchByDomain/{domain}")
//    public String searchByDomain(@PathVariable String domain, Model model) {
//        List<EmployeeDetailsDTO> employees = adminService.findByDomain(domain);
//        model.addAttribute("employees", employees);
//        return "employees";
//    }
//
//    @GetMapping("/searchBySubdomain/{subdomain}")
//    public String searchBySubdomain(@PathVariable String subdomain, Model model) {
//        List<EmployeeDetailsDTO> employees = adminService.findBySubdomain(subdomain);
//        model.addAttribute("employees", employees);
//        return "employees";
//    }
//
//    @GetMapping("/searchByProficiency/{proficiency}")
//    public String searchByProficiency(@PathVariable String proficiency, Model model) {
//        List<EmployeeDetailsDTO> employees = adminService.findByProficiency(proficiency);
//        model.addAttribute("employees", employees);
//        return "employees";
//    }
    
    

    
    @GetMapping("/searchEmployees")
    @PreAuthorize("hasRole('ADMIN')")
    public String searchEmployees(
            @RequestParam(required = false) String empid,
            @RequestParam(required = false) String skillname,
            @RequestParam(required = false) String domain,
            @RequestParam(required = false) String subdomain,
            @RequestParam(required = false) String proficiency,
            Model model) {

        List<EmployeeDetailsDTO> employees;

        // Perform the search with non-empty parameters
        employees = adminService.searchEmployees(empid, skillname, domain, subdomain, proficiency);

        model.addAttribute("employees", employees);
        return "employees";
    }
    
   
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAdminDashboard(Model model) {
        return "admin"; // This corresponds to the name of your Thymeleaf HTML file without the extension
    }
    
}
