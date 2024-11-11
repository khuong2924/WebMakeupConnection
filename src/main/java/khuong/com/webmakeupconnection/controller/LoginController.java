package khuong.com.webmakeupconnection.controller;

import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.repository.UserRepository;
import khuong.com.webmakeupconnection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
public class LoginController {

//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/login")
//    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model) {
//        boolean isAuthenticated = userService.validateUser(username, password);
//
//        if (isAuthenticated) {
//            return "redirect:/home";
//        } else {
//            model.addAttribute("error", "Password incorrect");
//            return "login";
//        }
//    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        // Kiểm tra tài khoản và mật khẩu
        boolean isAuthenticated = userService.validateUser(username, password);

        User getUser = userService.validateGetUser(username, password);

        if (isAuthenticated) {
            // Lưu thông tin người dùng vào session khi đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("username", username);// Lưu username vào session
            session.setAttribute("user", getUser);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            return "login";
        }


//    // Kiểm tra tài khoản và mật khẩu
//    boolean isAuthenticated = userService.validateUser(username, password);
//
//
//
//        if (isAuthenticated) {
//        // Lưu thông tin người dùng vào session khi đăng nhập thành công
//        HttpSession session = request.getSession();
//        session.setAttribute("username", username);  // Lưu username vào session
//        return "redirect:/home";
//    } else {
//        model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
//        return "login";
//    }
}



    @GetMapping("/checkSession")
    public String checkSession(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);  // false để không tạo mới session nếu không tồn tại

        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            User user = (User) session.getAttribute("user");
            model.addAttribute("message", "User logged in: " + username + " and usernameID: " + user.getId());
        } else {
            model.addAttribute("message", "No user logged in.");
        }

        return "checkSession";  // Trang hiển thị kết quả kiểm tra session
    }
//    @GetMapping("/checkSession")
//    public String checkSession(HttpServletRequest request, Model model) {
//        HttpSession session = request.getSession(false);  // false để không tạo mới session nếu không tồn tại
//
//        if (session != null && session.getAttribute("userId") != null) {
//            Long userId = (Long) session.getAttribute("userId");
//            model.addAttribute("message", "User logged in with ID: " + userId);
//        } else {
//            model.addAttribute("message", "No user logged in.");
//        }
//
//        return "checkSession";
//    }
}
