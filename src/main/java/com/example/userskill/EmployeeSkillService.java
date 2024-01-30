package com.example.userskill;


import com.example.dto.EmployeeDetailsDTO;
import com.example.repository.Empskillrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeSkillService {

    private final Empskillrepo empskillrepo;

    @Autowired
    public EmployeeSkillService(Empskillrepo empskillrepo) {
        this.empskillrepo = empskillrepo;
    }

    public EmployeeDetailsDTO getUserSkills(String empid) {
        return empskillrepo.findUserSkills(empid);
    }
}
