package com.odoraf.resumebuilder;

import com.odoraf.resumebuilder.models.Education;
import com.odoraf.resumebuilder.models.Job;
import com.odoraf.resumebuilder.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

//Controller holds business logic of the application
@Controller
public class HomeController {

    //Inject userProfileRepository
    @Autowired
    UserProfileRepository userProfileRepository;

    @GetMapping("/")
    public String home(){
        //filler code

        //user profile takes from sql file
        Optional<UserProfile> profileOptional = userProfileRepository.findByUserName("od");
        profileOptional.orElseThrow(() -> new RuntimeException("Not found: "));


        //My own sample resume data
        UserProfile profile1 = profileOptional.get();
        //new job obj
        Job job1 = new Job();
        job1.setCompany("Enove");
        job1.setOccupationTitle("Full Stack Engineer");
//        job1.setId(1);
        job1.setStartDate(LocalDate.of(2020, 8,1));
        job1.setEndDate(LocalDate.of(2021,8,1));
        job1.getResponsibilities().add(
                "Collaborated closely with the lead designer to " +
                        "appropriately shape the application to the " +
                        "needs of the business "
        );
        job1.getResponsibilities().add("Design custom Rest API’s integrating paypal using python");
        job1.getResponsibilities().add("Provisioned PostgreSQL database on AWS and deployed application on Heroku");
        job1.setCurrentJob(true);

        //second new job obj
        Job job2 = new Job();
        job2.setCompany("UCSD Police Department");
        job2.setOccupationTitle("Community Service Officer");
//        job2.setId(2);
        job2.setStartDate(LocalDate.of(2017, 10,1));
        job2.setEndDate(LocalDate.of(2020,3,1));
        job2.getResponsibilities().add(
                "Worked various shifts regarding campus security such as patrols " +
                "escort services for students special event security " +
                "facility lockups ect."
        );
        job2.getResponsibilities().add(
                "Promoted within one year taking on extra responsibility as the lead officer " +
                "1-2 nights a week managing equipment distribution" +
                "dispatching shift information and reassigning shifts if necessary"
        );
        job2.setCurrentJob(false);

        //insert jobs into profile obj
        //clear for users adding new jobs
        profile1.getJobs().clear();
        profile1.getJobs().add(job1);
        profile1.getJobs().add(job2);

        Education e1 = new Education();
        e1.setSchool("University of California San Diego");
        e1.setDegree("BS Cognitive Science w/ Specialization in Machine Learning and Neural Computation");
        e1.setIsStudent(true);
        e1.setStartDate(LocalDate.of(2016, 9,1));
        e1.setEndDate(LocalDate.of(2021,8,1));

//        Education e2 = new Education();
//        e2.setSchool("University of California San Diego");
//        e2.setDegree("BS Cognitive Science w/ Specialization in Machine Learning and Neural Computation");
//        e2.setIsStudent(true);
//        e2.setStartDate(LocalDate.of(2019, 5,1));
//        e2.setEndDate(LocalDate.of(2020,1,1));

        //clear for making edits
        profile1.getEducation().clear();
        profile1.getEducation().add(e1);
//        profile1.getEducation().add(e2);

        profile1.getSkills().clear();
        profile1.getSkills().add("Java");
        profile1.getSkills().add("Python");
        profile1.getSkills().add("JavaScript");
        profile1.getSkills().add("postgreSQL");
        profile1.getSkills().add("Spring/ Spring Boot");
        profile1.getSkills().add("Django");
        profile1.getSkills().add("React");
        profile1.getSkills().add("AWS");
        profile1.getSkills().add("Digital Ocean");
        profile1.getSkills().add("Heroku");



        System.out.println(profile1);

        //save profile into user repository
        userProfileRepository.save(profile1);
        return "index";
    }

    //Principal is the currently logged-in user
    @GetMapping("/edit")
    public String edit(Model model, Principal principal, @RequestParam(required = false) String add){
        String userId = principal.getName();
        model.addAttribute("userId", userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));
        UserProfile userProfile = userProfileOptional.get();
        if ("job".equals(add)){
            userProfile.getJobs().add(new Job());

        } else if ("education".equals(add)){
            userProfile.getEducation().add(new Education());
        } else if("skill".equals(add)){
            userProfile.getSkills().add("");
        }
        model.addAttribute("userProfile", userProfile);
        return "profile-edit";
    }

    @GetMapping ("/delete")
    public String delete(Model model, Principal principal, @RequestParam String type, @RequestParam int index) {
        String userId = principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));
        UserProfile userProfile = userProfileOptional.get();

        if ("job".equals(type)){
            userProfile.getJobs().remove(index);

        } else if ("education".equals(type)){
            userProfile.getEducation().remove(index);
        } else if("skill".equals(type)){
            userProfile.getSkills().remove(index);
        }
        userProfileRepository.save(userProfile);

        return "redirect:/edit";
    }



    //Need post mapping to actually make edits to the page
    //Model attribute gives the form submission as user profile objects
    @PostMapping("/edit")
    public String postEdit(Model model, Principal principal, @ModelAttribute UserProfile userProfile){
        //find and id from old user profile via current user's username
        String userName = principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userName);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userName));
        UserProfile savedUserProfile = userProfileOptional.get();
        //reassign same id username as old user profile
        userProfile.setId(savedUserProfile.getId());
        userProfile.setUserName(userName);
        userProfileRepository.save(userProfile);
        return "redirect:/view/" + userName;
    }



    @GetMapping("/view/{userId}")
    public String view(Principal principal, @PathVariable String userId, Model model){
        if (principal != null && principal.getName() != null){
            boolean currentUserProfile = principal.getName().equals(userId);
            model.addAttribute("currentUserProfile", currentUserProfile);
        }
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
