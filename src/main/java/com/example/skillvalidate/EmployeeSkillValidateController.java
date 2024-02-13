package com.example.skillvalidate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.EmployeeSkill;
import com.example.userskill.EmployeeSkillService;

@Controller
public class EmployeeSkillValidateController {

    @Autowired
    private EmployeeSkillValidateService EmployeeSkillValidateService;

    // Endpoint to display employee skill details
    @GetMapping("/employeeSkills")
    public String showEmployeeSkills(Model model) {
        List<EmployeeSkill> employeeSkills = EmployeeSkillValidateService.getAllEmployeeSkills();
        model.addAttribute("employeeSkills", employeeSkills);
        return "employeeSkillsPage"; // Assuming the name of your JSP file is employeeSkillsPage.jsp
    }

    // Endpoint to handle review action
    @PostMapping("/reviewSkill")
    public String reviewSkill(@RequestParam String empid, @RequestParam Integer skillid) {
    	EmployeeSkillValidateService.reviewEmployeeSkill(empid, skillid);
        return "redirect:/employeeSkills"; // Redirect back to the page displaying employee skills
    }
}
