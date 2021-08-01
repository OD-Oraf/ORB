package com.odoraf.resumeportal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "Hello";
    }

    @GetMapping("/edit")
    public String edit(){
        return "Edit page";
    }

    @GetMapping("/view/{userId}")
    public String view(@PathVariable String userId, Model model){
        model.addAttribute("userId", userId);
        //return statement links to templates in resources due to tymleaf configuration
        return "profile-templates/3/index";
    }
}
