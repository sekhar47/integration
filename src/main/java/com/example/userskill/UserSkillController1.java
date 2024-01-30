package com.example.userskill;

import com.example.dto.EmployeeDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserSkillController1 {

    private final EmployeeSkillService employeeSkillService;

    @Autowired
    public UserSkillController1(EmployeeSkillService employeeSkillService) {
        this.employeeSkillService = employeeSkillService;
    }

    @GetMapping("/employee/skills")
    public String getUserSkills(Model model, Principal principal) {
        String loggedInUserId = principal.getName();
        EmployeeDetailsDTO userSkills = employeeSkillService.getUserSkills(loggedInUserId);
        model.addAttribute("userSkills", userSkills);
        return "userSkills";
    }
}
