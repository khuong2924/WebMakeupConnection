package khuong.com.webmakeupconnection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "homePage";
    }

    @GetMapping("/home")
    public String home() {
        return "homePage";
    }

    @GetMapping("/profile")
    public String showProfilePage() {
        return "profile";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signUp";
    }

    @GetMapping("/chatBox")
    public String showChatBoxPage() {
        return "chatBox";
    }

    @GetMapping("/account")
        public String showAccountPage() {
            return "account";
        }
}
