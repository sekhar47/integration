package com.example.userprofile;


import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private UserProfileService userService;

    @Autowired
    public ProfileController(UserProfileService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        // Fetch logged-in user's profile and add it to the model
        String loggedInEmpId = principal.getName(); // Get the logged-in user's empid (username)
        User user = userService.getUserByEmpid(loggedInEmpId);

        if (user != null) {
            model.addAttribute("user", user);
        } else {
            // Handle the case when user is not found
        }

        return "profile";
    }

//    @PostMapping("/profile/update")
//    public String updateProfile(@RequestParam("profilePicture") MultipartFile profilePicture, Principal principal) {
//        try {
//            String loggedInEmpId = principal.getName(); // Get the logged-in user's empid (username)
//            User user = userService.getUserByEmpid(loggedInEmpId);
//
//            if (user != null && profilePicture != null && !profilePicture.isEmpty()) {
//                user.setProfilePicture(profilePicture.getBytes());
//                userService.updateUserProfile(user);
//            }
//        } catch (IOException e) {
//            // Handle exception
//        }
//        return "redirect:/profile";
//    }
    

    @PostMapping("/uploadProfilePicture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture, Principal principal) {
        try {
            String loggedInEmpId = principal.getName();
            userService.updateProfilePicture(loggedInEmpId, profilePicture);
            return ResponseEntity.ok("Profile picture uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload profile picture");
        }
    }

}
