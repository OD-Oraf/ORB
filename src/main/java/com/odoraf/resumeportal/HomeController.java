package com.odoraf.resumeportal;

import com.odoraf.resumeportal.models.Education;
import com.odoraf.resumeportal.models.Job;
import com.odoraf.resumeportal.models.MyUserDetails;
import com.odoraf.resumeportal.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//Controller holds business logic of the application
@Controller
public class HomeController {

    //Inject userProfileRepository
    @Autowired
    UserProfileRepository userProfileRepository;

    @GetMapping("/")
    public String home(){
        //user profile takes from sql file
        Optional<UserProfile> profileOptional = userProfileRepository.findByUserName("einstein");
        profileOptional.orElseThrow(() -> new RuntimeException("Not found: "));

        UserProfile profile1 = profileOptional.get();
        //new job obj
        Job job1 = new Job();
        job1.setCompany("Company 1");
        job1.setDesignation("Designation");
        job1.setId(1);
        job1.setStartDate(LocalDate.of(2020, 1,1));
        job1.getResponsibilities().add("Invent theory of relativity");
        job1.getResponsibilities().add("Advanced quantum mechanics");
        job1.getResponsibilities().add("Some other incredible stuff");
        job1.setCurrentJob(true);

        //second new job obj
        Job job2 = new Job();
        job2.setCompany("Company 2");
        job2.setDesignation("Designation");
        job2.setId(2);
        job2.setStartDate(LocalDate.of(2019, 5,1));
        job2.setEndDate(LocalDate.of(2020,1,1));
        job2.getResponsibilities().add("Invent theory of relativity");
        job2.getResponsibilities().add("Advanced quantum mechanics");
        job2.getResponsibilities().add("Some other incredible stuff");

        //insert jobs into profile obj
        //clear for users adding new jobs
        profile1.getJobs().clear();
        profile1.getJobs().add(job1);
        profile1.getJobs().add(job2);

        Education e1 = new Education();
        e1.setSchool("University of California San Diego");
        e1.setDegree("BS Cognitive Science w/ Specialization in Machine Learning and Neural Computation");
        e1.setIsStudent(true);
        e1.setStartDate(LocalDate.of(2019, 5,1));
        e1.setEndDate(LocalDate.of(2020,1,1));

        Education e2 = new Education();
        e2.setSchool("University of California San Diego");
        e2.setDegree("BS Cognitive Science w/ Specialization in Machine Learning and Neural Computation");
        e2.setIsStudent(true);
        e2.setStartDate(LocalDate.of(2019, 5,1));
        e2.setEndDate(LocalDate.of(2020,1,1));

        //clear for making edits
        profile1.getEducation().clear();
        profile1.getEducation().add(e1);
        profile1.getEducation().add(e2);

        profile1.getSkills().clear();
        profile1.getSkills().add("Quantum Physics");
        profile1.getSkills().add("Modern physics");
        profile1.getSkills().add("Violin");
        profile1.getSkills().add("Philosophy");



        System.out.println(profile1);

        //save profile into user repository
        userProfileRepository.save(profile1);
        return "profile";
    }

    //Principal is the currently logged-in user
    @GetMapping("/edit")
    public String edit(Model model, Principal principal){
        String userId = principal.getName();
        model.addAttribute("userId", userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));
        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile", userProfile);
        return "profile-edit";
    }

    //Need post mapping to actually make edits to the page
    @PostMapping("/edit")
    public String postEdit(Model model, Principal principal){
        String userId = principal.getName();
        return "redirect:/view/" + userId;
    }



    @GetMapping("/view/{userId}")
    public String view(@PathVariable String userId, Model model){
        //If user is not found in the user repository than return user
        //Use optional so that we return user not found instead of null exception
        //lambda expression to throw exception
        //userProfileOptional return user profile associated with userId
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));

        //addAttribute just adds the object into the model object
        //Ends up being an object of objects
        model.addAttribute("userId", userId);

        //if that user profile exists then get it from the user profile optional
        //Basically holds application data
        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile", userProfile);

//        System.out.println(model.toString());
        System.out.println(userProfileOptional.get());

        //return statement links to templates in resources due to tymleaf configuration
        return "profile-templates/" + userProfile.getTheme() + "/index";
    }
}
