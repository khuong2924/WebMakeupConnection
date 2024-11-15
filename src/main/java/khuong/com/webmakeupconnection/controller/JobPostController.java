package khuong.com.webmakeupconnection.controller;

import jakarta.servlet.http.HttpSession;
import khuong.com.webmakeupconnection.config.SessionUtils;
import khuong.com.webmakeupconnection.dto.JobPostDTO;
import khuong.com.webmakeupconnection.dto.ProfileDTO;
import khuong.com.webmakeupconnection.dto.ResponseDTO;
import khuong.com.webmakeupconnection.entity.JobPost;
import khuong.com.webmakeupconnection.entity.Profile;
import khuong.com.webmakeupconnection.repository.JobPostRepository;
import khuong.com.webmakeupconnection.repository.UserRepository;
import khuong.com.webmakeupconnection.service.JobPostService;
import khuong.com.webmakeupconnection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/jobPost")
@RequiredArgsConstructor
public class JobPostController {


    private final JobPostService jobPostService;
    private final UserService userService;
    private static final Logger logger = Logger.getLogger(JobPostController.class.getName());
    private final UserService userService2;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobPostRepository jobPostRepository;
    @Autowired
    private HttpSession httpSession;

    private final SessionUtils sessionUtils;

    @GetMapping
    public ResponseDTO<List<JobPostDTO>> getAllJobPost() {
        ResponseDTO<List<JobPostDTO>> dto = new ResponseDTO<>();
        dto.setData(jobPostService.getAll());
        dto.setStatus(200);
        return dto;
    }



    @GetMapping("/all")
    public ResponseDTO<List<JobPostDTO>> getAll() {
        ResponseDTO<List<JobPostDTO>> dto = new ResponseDTO<>();
        dto.setData(jobPostService.getAll());
        dto.setStatus(200);
        return dto;
    }

    @PutMapping("{id}")
    public ResponseDTO<Void> update(@PathVariable("id") Long id, @RequestBody JobPostDTO jobPostDTO) {
        jobPostService.update(id, jobPostDTO);
        return ResponseDTO.<Void>builder()
                .status(201)
                .message("Sua thanh cong profile")
                .build();
    }

    @DeleteMapping("{id}")
    public ResponseDTO<Void> delete(@PathVariable("id") Long id) {
        jobPostService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(201)
                .message("Xoa thanh cong nguoi dung")
                .build();
    }

    @GetMapping("/jobPosts")
    public String getProfiles(Model model) {
        List<JobPostDTO> jobpost = jobPostService.getAll();
        model.addAttribute("users", jobpost);
        return "jobPosts";
    }


    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/{jobPostId}/updateCoverPhoto")
    public String updateCoverPhoto(@PathVariable("jobPostId") Long jobPostId, @RequestParam("file") MultipartFile file) throws IOException {
        String img_url = imageUploadService.uploadImage(file);
        JobPost jpost = jobPostRepository.findById(jobPostId).orElseThrow(RuntimeException::new);
        jpost.setPostPhoto(img_url);
        jobPostRepository.save(jpost);
        return "Post photo updated successfully";
    }

    //Endpoint để cập nhật job_post:
    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO<Void>> createOrUpdateJobPost(
            @RequestParam("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("jobType") String jobType,
            @RequestParam("location") String location,
            @RequestParam("salary") BigDecimal salary,
            @RequestParam(value = "postPhoto", required = false) MultipartFile postPhoto,
            @RequestParam("status") String status,
            @RequestParam("createAt") LocalDateTime createAt) throws IOException {

        String postPhotoUrl = null;

        if (postPhoto != null && !postPhoto.isEmpty()) {
            postPhotoUrl = imageUploadService.uploadImage(postPhoto);
        }

        JobPost jobPost = jobPostRepository.findById(userId).orElseThrow(RuntimeException::new);
        System.out.println("Before P: " + jobPost.getPostPhoto());
        String afterP = jobPost.getPostPhoto();

        jobPost.setTitle(title);
        jobPost.setDescription(description);
        jobPost.setJobType(jobType);
        jobPost.setLocation(location);
        jobPost.setSalary(salary);
        jobPost.setPostPhoto(postPhotoUrl);
        jobPost.setStatus(status);

        if (postPhotoUrl != null) {
            jobPost.setPostPhoto(postPhotoUrl);
        }

        System.out.println("After P: " + jobPost.getPostPhoto());

        jobPostRepository.save(jobPost);

        ResponseDTO<Void> dto = new ResponseDTO<>();
        dto.setStatus(200);
        dto.setMessage("Job post created successfully");

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/profile")
                .body(dto);
    }












}









