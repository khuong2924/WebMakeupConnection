package khuong.com.webmakeupconnection.controller;


import khuong.com.webmakeupconnection.dto.ProfileDTO;
import khuong.com.webmakeupconnection.dto.ResponseDTO;
import khuong.com.webmakeupconnection.service.ProfileService;
import khuong.com.webmakeupconnection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private static final Logger logger = Logger.getLogger(ProfileController.class.getName());
    private final UserService userService;

    @GetMapping
    public ResponseDTO<List<ProfileDTO>> getAll() {
        ResponseDTO<List<ProfileDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(profileService.getAll());
        responseDTO.setStatus(200);
        return responseDTO;

    }

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody ProfileDTO profileDTO) {
        profileService.create(profileDTO);
        ResponseDTO<Void> responseDTO = ResponseDTO.<Void>builder()
                .status(201)
                .message("Tao thanh cong profile")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO).getBody();
    }

    @PutMapping("{id}")
    public ResponseDTO<Void> update(@PathVariable("id") Long id, @RequestBody ProfileDTO profileDTO) {
        profileService.update(id, profileDTO);
        return ResponseDTO.<Void>builder()
                .status(201)
                .message("Sua thanh cong profile")
                .build();
    }

    @DeleteMapping("{id}")
    public ResponseDTO<Void> delete(@PathVariable("id") Long id) {
        profileService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(201)
                .message("Xoa thanh cong nguoi dung")
                .build();
    }

    @GetMapping("/profiles")
    public String getProfiles(Model model) {
        List<ProfileDTO> profiles= profileService.getAll();
        model.addAttribute("users", profiles);
        return "profiles";
    }



}






