package com.genz.socio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String homePage() { return "home";}

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping("/signup")
    public String signupPage() { return "signup"; }

    @GetMapping("/profile-view")
    public String profilePage() { return "profile"; }

    @GetMapping("/edit-profile")
    public String editProfilePage() {
        return "edit-profile";
    }
}