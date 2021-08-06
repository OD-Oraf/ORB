package com.odoraf.resumeportal;

import com.odoraf.resumeportal.models.Job;
import com.odoraf.resumeportal.models.MyUserDetails;
import com.odoraf.resumeportal.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        job1.getDesignation("Designation");
        job1.setId(1);
        job1.setStartDate(LocalDate.of(2020, 1,1));
        job1.setEndDate(LocalDate.of(2020,3,1));

        //second new job obj
        Job job2 = new Job();
        job2.setCompany("Company 2");
        job2.getDesignation("Designation");
        job2.setId(2);
        job2.setStartDate(LocalDate.of(2019, 5,1));
        job2.setEndDate(LocalDate.of(2020,1,1));

        //insert jobs into profile obj
        //clear for users adding new jobs
        profile1.getJobs().clear();
        profile1.getJobs().add(job1);
        profile1.getJobs().add(job2);

        System.out.println(profile1);

        //save profile into user repository
//        userProfileRepository.save(profile1);
        return "profile";
    }

    @GetMapping("/edit")
    public String edit(){
        return "Edit page";
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
