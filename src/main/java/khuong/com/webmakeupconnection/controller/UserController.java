package khuong.com.webmakeupconnection.controller;

import khuong.com.webmakeupconnection.dto.ResponseDTO;
import khuong.com.webmakeupconnection.dto.UserDTO;
import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.repository.UserRepository;
import khuong.com.webmakeupconnection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseDTO<List<UserDTO>> getAll() {
        ResponseDTO<List<UserDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(userService.getAll());
        responseDTO.setStatus(200);
        return responseDTO;
    }

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody UserDTO userDTO) {
        userService.create(userDTO);
        ResponseDTO<Void> response = ResponseDTO.<Void>builder()
                .status(201)
                .message("Sua thanh cong nguoi dung")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response).getBody();
    }

    @PutMapping("{id}")
    public ResponseDTO<Void> update(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        userService.update(id, userDTO);
        return ResponseDTO.<Void>builder()
                .status(201)
                .message("Sua thanh cong nguoi dung")
                .build();
    }

    @DeleteMapping("{id}")
    public ResponseDTO<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(201)
                .message("Xoa thanh cong nguoi dung")
                .build();
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<UserDTO> users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    //upload image:
    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/{userId}/updateProfilePicture")
    public String updateProfilePicture(@PathVariable Long userId, @RequestParam("file") MultipartFile file) throws IOException, IOException {
        String imageUrl = imageUploadService.uploadImage(file);

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPortraitPhoto(imageUrl);
        userRepository.save(user);
        return "Profile picture updated successfully";
    }


    //lay username nguoi dung dang dang nhap:
//    @GetMapping("/current")
//    public String getCurrentUsername(@AuthenticationPrincipal UserDetails userDetails) {
//        return userDetails.getUsername();
//    }
//    @GetMapping("/current")
//    public String getCurrentUser(Principal principal) {
//        return principal.getName();
//    }
}
