package khuong.com.webmakeupconnection.controller;


import jakarta.servlet.http.HttpSession;
import khuong.com.webmakeupconnection.config.SessionUtils;
import khuong.com.webmakeupconnection.dto.ProfileDTO;
import khuong.com.webmakeupconnection.dto.ResponseDTO;
import khuong.com.webmakeupconnection.entity.Profile;
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
import java.util.List;
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
            @RequestParam("user_id") Long user_id,
            @RequestParam("fullName") String fullName,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("gender") String gender,
            @RequestParam("bio") String bio,
            @RequestParam("address") String address,
            @RequestParam("portfolioPhoto") MultipartFile portfolioPhoto,
            @RequestParam("coverPhoto") MultipartFile coverPhoto) throws IOException {

        // Upload ảnh
        String portfolioPhotoUrl = imageUploadService.uploadImage(portfolioPhoto);
        String coverPhotoUrl = imageUploadService.uploadImage(coverPhoto);

        // Map các dữ liệu vào ProfileDTO
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUser_id(SessionUtils.getCurrentUserId());
        profileDTO.setFullName(fullName);
        profileDTO.setBirthDate(birthDate);
        profileDTO.setGender(gender);
        profileDTO.setBio(bio);
        profileDTO.setAddress(address);
        profileDTO.setPortfolioPhoto(portfolioPhotoUrl);
        profileDTO.setCoverPhoto(coverPhotoUrl);

        // Gọi service để lưu hoặc cập nhật profile
        profileService.create(profileDTO);

        // Trả về ResponseDTO
        ResponseDTO<Void> responseDTO = ResponseDTO.<Void>builder()
                .status(201)
                .message("Profile created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

}






