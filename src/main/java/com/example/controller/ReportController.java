package com.example.controller;


import com.example.dto.EmployeeDetailsDTO;
import com.example.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/report")
public class ReportController {

    private final EmployeeService employeeService;

    @Autowired
    public ReportController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

   
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public String showSearchPage() {
        return "search"; // This corresponds to the filename of your HTML for the search page
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generate")
    public String generateReport(
            @RequestParam(required = false) String empid,
            Model model
    ) {
        List<EmployeeDetailsDTO> employees = employeeService.findByEmpid(empid);
        model.addAttribute("employees", employees);
        return "report"; // This corresponds to the filename of your HTML for the report page
    }

    
    @GetMapping("/download")
    @PreAuthorize("hasRole('ADMIN')")
    public void downloadReport(
            @RequestParam(required = false) String empid,
            HttpServletResponse response
    ) throws IOException {
        // Get the data for the specified empid
        List<EmployeeDetailsDTO> employees = employeeService.findByEmpid(empid);

        // You can use a CSV library (e.g., OpenCSV) to write data to a CSV file
        // For simplicity, I'll use a basic CSV string here
        StringBuilder csvContent = new StringBuilder();
        // Append CSV headers
        csvContent.append("EmpID,Name,Email,Mobile,SkillName,Domain,Subdomain,Proficiency,Availability,Reviewed,TrainingDays\n");
        // Append data
        for (EmployeeDetailsDTO employee : employees) {
            csvContent.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                    employee.getEmpid(), employee.getName(), employee.getEmpemail(),
                    employee.getEmpmobile(), employee.getSkillname(), employee.getDomain(),
                    employee.getSubdomain(), employee.getProficiency(), employee.isAvailability(),
                    employee.isReviewed(), employee.getTrainingdays()));
        }

        // Set response headers
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=employee_report.csv");

        // Write CSV content to the response
        response.getWriter().write(csvContent.toString());
    }
}
