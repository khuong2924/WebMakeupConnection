package khuong.com.webmakeupconnection.controller;


import jakarta.servlet.http.HttpSession;
import khuong.com.webmakeupconnection.config.SessionUtils;
import khuong.com.webmakeupconnection.dto.ProfileDTO;
import khuong.com.webmakeupconnection.dto.ResponseDTO;
import khuong.com.webmakeupconnection.entity.Profile;
import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.repository.ProfileRepository;
import khuong.com.webmakeupconnection.repository.UserRepository;
import khuong.com.webmakeupconnection.service.ProfileService;
import khuong.com.webmakeupconnection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private static final Logger logger = Logger.getLogger(ProfileController.class.getName());
    private final UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private HttpSession httpSession;

    private final SessionUtils sessionUtils;

    Long currentUserId = SessionUtils.getCurrentUserId();

    @GetMapping
    public ResponseDTO<List<ProfileDTO>> getAllProfiles() {
        ResponseDTO<List<ProfileDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(profileService.getAll());
        responseDTO.setStatus(200);

        return responseDTO;
    }




    @GetMapping("/all")
    public ResponseDTO<List<ProfileDTO>> getAll() {
        ResponseDTO<List<ProfileDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(profileService.getAll());
        responseDTO.setStatus(200);
        return responseDTO;

    }

//    @PostMapping
//    public ResponseDTO<Void> create(@RequestBody ProfileDTO profileDTO) {
//        profileService.create(profileDTO);
//        ResponseDTO<Void> responseDTO = ResponseDTO.<Void>builder()
//                .status(201)
//                .message("Tao thanh cong profile")
//                .build();
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO).getBody();
//    }

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

    //upload image:
    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/{profileId}/updateCoverPhoto")
    public String updateCoverPhoto(@PathVariable("profileId") Long profileId, @RequestParam("file") MultipartFile file) throws IOException {
        String img_url = imageUploadService.uploadImage(file);
        Profile profile = profileRepository.findById(profileId).orElseThrow(RuntimeException::new);
        profile.setCoverPhoto(img_url);
        profileRepository.save(profile);
        return "Cover photo updated successfully";
    }


    @PostMapping("/{profileId}/updatePortfolioPhoto")
    public String updatePortfolioPhoto(@PathVariable("profileId") Long profileId, @RequestParam("file") MultipartFile file) throws IOException {
        String img_url = imageUploadService.uploadImage(file);
        Profile profile = profileRepository.findById(profileId).orElseThrow(RuntimeException::new);
        profile.setPortfolioPhoto(img_url);
        profileRepository.save(profile);
        return "PortfolioPhoto photo updated successfully";
    }


//        @PostMapping("/{profileId}/update")
//        public ResponseDTO<Void> updateProfile(@PathVariable("profileId") Long profileId, @RequestBody ProfileDTO profileDTO) {
//            profileService.update(profileId, profileDTO);
//            return ResponseDTO.<Void>builder()
//                    .status(201)
//                    .message("Sửa thành công profile")
//                    .build();
//        }


    // Endpoint để cập nhật profile
    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO<Void>> createOrUpdateProfile(
            @RequestParam("user_id") Long userId,
            @RequestParam("fullName") String fullName,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("gender") String gender,
            @RequestParam("bio") String bio,
            @RequestParam("address") String address,
            @RequestParam(value = "portfolioPhoto", required = false) MultipartFile portfolioPhoto,
            @RequestParam(value = "coverPhoto", required = false) MultipartFile coverPhoto) throws IOException {

        String portfolioPhotoUrl = null;
        String coverPhotoUrl = null;
//        String portfolioPhotoUrl = "https://res.cloudinary.com/dhp7ylyvh/image/upload/v1731477517/qljywamnvnwb8mi0rqjt.jpg";
//        String coverPhotoUrl = "https://res.cloudinary.com/dhp7ylyvh/image/upload/v1731477640/jthwtgc7gir7vczbloch.png";

        if (portfolioPhoto != null && !portfolioPhoto.isEmpty()) {
            portfolioPhotoUrl = imageUploadService.uploadImage(portfolioPhoto);
        }

        if (coverPhoto != null && !coverPhoto.isEmpty()) {
            coverPhotoUrl = imageUploadService.uploadImage(coverPhoto);
        }

        Profile profile = profileRepository.findByUserId(userId).get();
        System.out.println("Before P: " + profile.getPortfolioPhoto());
        System.out.println("Before C: " + profile.getCoverPhoto());

        String afterP = profile.getPortfolioPhoto();
        String afterC = profile.getCoverPhoto();

        profile.setFullName(fullName);
        profile.setBirthDate(birthDate);
        profile.setGender(gender);
        profile.setBio(bio);
        profile.setAddress(address);

        if(portfolioPhotoUrl != null) {
            profile.setPortfolioPhoto(portfolioPhotoUrl);
        } else {
            profile.setPortfolioPhoto(afterP);
        }
        if(coverPhotoUrl != null) {
            profile.setCoverPhoto(coverPhotoUrl);
        } else {
            profile.setCoverPhoto(afterC);
        }
        System.out.println("After P: " + profile.getPortfolioPhoto());
        System.out.println("After C: " + profile.getCoverPhoto());

        profileRepository.save(profile);

        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        responseDTO.setStatus(200);
        responseDTO.setMessage("Profile updated successfully");

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/profile")
                .body(responseDTO);
    }

}






