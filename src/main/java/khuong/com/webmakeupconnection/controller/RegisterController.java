package khuong.com.webmakeupconnection.controller;

import khuong.com.webmakeupconnection.dto.UserDTO;
import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        ResponseEntity<String> response = null;

        try {
            User savedUser = userService.createUser(user);
            if (savedUser.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED)
                        .body("User is created successfully for " + savedUser.getUsername());

            }
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: " + e.getMessage());
        }
        return response;
    }



}
