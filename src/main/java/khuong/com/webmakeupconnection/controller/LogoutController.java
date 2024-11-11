package khuong.com.webmakeupconnection.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

    @RequestMapping("/logout")
    public String logout(HttpSession session) {

        session.removeAttribute("username");
        return "redirect:/login";
    }
}
