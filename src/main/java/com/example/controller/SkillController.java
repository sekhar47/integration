package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.dto.UpdateSkillForm;
import com.example.entity.Skills;
import com.example.service.SkillService;

@Controller
public class SkillController {

    @Autowired
    private SkillService service;

    @GetMapping("/viewSkillsPage")
    public String viewSkillsPage(Model model) {
        List<Skills> skills = service.getSkills();
        model.addAttribute("skills", skills);
        return "viewSkills";
    }

    @GetMapping("/createDomainPage")
    public String createDomainPage() {
        return "createDomain";
    }

    @GetMapping("/createSubdomainPage")
    public String createSubdomainPage(Model model) {
        List<String> domains = service.getDistinctDomains();
        model.addAttribute("domains", domains);
        return "createSubdomain";
    }

    @GetMapping("/createSkillPage")
    public String createSkillPage(Model model) {
        List<String> domains = service.getDistinctDomains();
        model.addAttribute("domains", domains);

        // Add an empty Skills object to the model
        model.addAttribute("skill", new Skills());

        return "createSkill";
    }

    @PostMapping("/saveSkill")
    public String saveSkill(@ModelAttribute Skills skill) {
        // Check if a skill with the same name, domain, and subdomain already exists
        Skills existingSkill = service.getSkillByNameAndDomainAndSubdomain(skill.getSkillname(), skill.getDomain(), skill.getSubdomain());

        if (existingSkill != null) {
            // If the skill already exists, update its properties
            existingSkill.setSkillname(skill.getSkillname());
            // Update other properties if needed

            // Save the updated skill
            service.saveSkill(existingSkill);
        } else {
            // If the skill doesn't exist, check if there is a matching row with the same domain and subdomain
            List<Skills> skillsInSameDomainAndSubdomain = service.findByDomainAndSubdomain(skill.getDomain(), skill.getSubdomain());

            if (!skillsInSameDomainAndSubdomain.isEmpty()) {
                // If there are existing skills in the same domain and subdomain, find the first one with an empty skillname
                Skills skillWithEmptyName = skillsInSameDomainAndSubdomain.stream()
                        .filter(s -> s.getSkillname() == null || s.getSkillname().trim().isEmpty())
                        .findFirst()
                        .orElse(null);

                if (skillWithEmptyName != null) {
                    // If found, update the existing skill with the new skillname
                    skillWithEmptyName.setSkillname(skill.getSkillname());
                    // Update other properties if needed

                    // Save the updated skill
                    service.saveSkill(skillWithEmptyName);
                } else {
                    // If no skill with an empty name is found, create a new skill
                    service.saveSkill(skill);
                }
            } else {
                // If there are no existing skills in the same domain and subdomain, create a new skill
                service.saveSkill(skill);
            }
        }

        return "redirect:/viewSkillsPage";
    }


    @GetMapping("/updateSkill")
    public String showUpdateSkillPage(Model model) {
        List<String> domains = service.getDomains();
        model.addAttribute("domains", domains);

        // Check if a domain is selected
        String selectedDomain = (String) model.getAttribute("selectedDomain");
        if (selectedDomain != null) {
            List<String> subdomains = service.getSubdomainsByDomain(selectedDomain);
            model.addAttribute("subdomains", subdomains);

            // Check if a subdomain is selected
            String selectedSubdomain = (String) model.getAttribute("selectedSubdomain");
            if (selectedSubdomain != null) {
                List<String> skillNames = service.getSkillNamesBySubdomain(selectedSubdomain);
                model.addAttribute("skillNames", skillNames);
            }
        }

        model.addAttribute("updateSkillForm", new UpdateSkillForm());

        return "updateSkill";
    }
    @PostMapping("/updateSkill")
    public String updateSkillName(UpdateSkillForm updateSkillForm, Model model) {
        String domain = updateSkillForm.getDomain();
        String subdomain = updateSkillForm.getSubdomain();
        String oldSkillName = updateSkillForm.getSkillName();
        String newSkillName = updateSkillForm.getNewSkillName();

        service.updateSkillName(domain, subdomain, oldSkillName, newSkillName);

        // Redirect to the update skill page after successful update
        return "redirect:/updateSkill";
    }

    
    @GetMapping("/deleteSkillPage")
    public String deleteSkillPage(@RequestParam("skillname") String skillName, Model model) {
        Skills skill = service.getSkillByName(skillName);
        model.addAttribute("skill", skill);
        return "deleteSkill";
    }

    @PostMapping("/deleteSkill")
    public String deleteSkill(@RequestParam("skillname") String skillName) {
        Skills skill = service.getSkillByName(skillName);
        if (skill != null) {
            service.deleteSkill(skill.getSkillid());
        }
        return "redirect:/viewSkillsPage";
    }

    @GetMapping("/getSubdomains/{domain}")
    @ResponseBody
    public List<String> getSubdomainsByDomain(@PathVariable String domain) {
        return service.getSubdomainsByDomain(domain);
    }

    @PostMapping("/saveDomain")
    public String saveDomain(@RequestParam("domain") String domain) {
        // Check if the domain already exists (case-insensitive)
        if (service.existsDomainIgnoreCase(domain)) {
            // If the domain already exists, you can handle this case, e.g., by redirecting with an error message
            return "redirect:/createDomainPage?error=Domain already exists";
        }

        // Save the new domain
        service.saveDomain(domain);

        return "redirect:/viewSkillsPage";
    }
    
    @PostMapping("/saveSubdomain")
    public String saveSubdomain(@RequestParam("domain") String domain, @RequestParam("subdomain") String subdomain, Model model) {
        // Check if the subdomain already exists for the given domain (case-insensitive)
        if (service.existsSubdomainIgnoreCase(domain, subdomain)) {
            model.addAttribute("errorMessage", "Subdomain under the chosen domain already exists.");
            // Add logic to populate the domains list again and pass the model to the view
            List<String> domains = service.getDistinctDomains();
            model.addAttribute("domains", domains);
            return "createSubdomain";
        }

        // Save the subdomain
        service.saveSubdomain(domain, subdomain);
        return "redirect:/viewSkillsPage";
    }

}
