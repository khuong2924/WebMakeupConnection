package khuong.com.webmakeupconnection.controller;

import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model) {
        boolean isAuthenticated = userService.validateUser(username, password);

        if (isAuthenticated) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Password incorrect");
            return "login";
        }
    }
}
