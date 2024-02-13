package com.example.skillvalidate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.EmployeeSkill;
import com.example.repository.Empskillrepo;

@Service
public class EmployeeSkillValidateService {
    
    @Autowired
    private Empskillrepo empskillrepo;

    public List<EmployeeSkill> getAllEmployeeSkills() {
        return empskillrepo.findAll();
    }

    public void reviewEmployeeSkill(String empid, Integer skillid) {
        empskillrepo.reviewSkill(empid, skillid);
    }
}
