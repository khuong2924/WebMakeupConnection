package khuong.com.webmakeupconnection.controller;


import khuong.com.webmakeupconnection.dto.ApplyInfoDTO;
import khuong.com.webmakeupconnection.dto.ResponseDTO;
import khuong.com.webmakeupconnection.entity.ApplyInfo;
import khuong.com.webmakeupconnection.entity.JobPost;
import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.repository.ApplyInfoRepository;
import khuong.com.webmakeupconnection.repository.JobPostRepository;
import khuong.com.webmakeupconnection.repository.UserRepository;
import khuong.com.webmakeupconnection.service.ApplyInfoService;
import khuong.com.webmakeupconnection.service.JobPostService;
import khuong.com.webmakeupconnection.service.UserService;
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
@RequestMapping("applyInfo")
public class ApplyInfoController {

    @Autowired
    private ApplyInfoService applyInfoService;
    @Autowired
    private JobPostService jobPostService;
    @Autowired
    private ApplyInfoRepository applyInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobPostRepository jobPostRepository;

    @GetMapping
    public ResponseDTO<List<ApplyInfoDTO>> getAllApplyInfo() {
        ResponseDTO<List<ApplyInfoDTO>> dto = new ResponseDTO<>();
        dto.setData(applyInfoService.getAll());
        dto.setStatus(200);
        return dto;
    }

    @GetMapping("/all")
    public ResponseDTO<List<ApplyInfoDTO>> getAll() {
        ResponseDTO<List<ApplyInfoDTO>> dto = new ResponseDTO<>();
        dto.setData(applyInfoService.getAll());
        dto.setStatus(200);
        return dto;
    }

    @GetMapping("{id}")
    public ResponseDTO<Void> update(@PathVariable("id") Long id, @RequestBody ApplyInfoDTO applyInfoDTO) {
        applyInfoService.update(id, applyInfoDTO);
        return ResponseDTO.<Void>builder()
                .status(201)
                .message("Sua thanh cong apply info")
                .build();
    }

    @DeleteMapping("{id}")
    public ResponseDTO<Void> delete(@PathVariable("id") Long id) {
        applyInfoService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(201)
                .message("Xoa thanh cong apply info")
                .build();
    }

    @GetMapping("/applyInfo")
    public String getApplyInfo(Model model) {
        List<ApplyInfoDTO> applyInfoDTOS = applyInfoService.getAll();
        model.addAttribute("applyInfoDTOS", applyInfoDTOS);
        return "applyInfo";
    }

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/{applyInfoId}/updateApplyImage")
    public String updateApplyImage(@PathVariable("applyInfoId") Long applyInfoId, @RequestParam("file") MultipartFile file) throws IOException {
        String img_url = imageUploadService.uploadImage(file);
        ApplyInfo apply = applyInfoRepository.findById(applyInfoId).orElseThrow(RuntimeException::new);
        apply.setApplyImage(img_url);
        applyInfoRepository.save(apply);
        return "Image apply updated sucessfully";
    }


//    @PostMapping("/registry")
//    public ResponseEntity<ResponseDTO<Void>> createOrUpdateApplyInfo(
//            @RequestParam("jobPostId") Long jobPostId,
//            @RequestParam("userId") Long userId,
//            @RequestParam("applyContent") String applyContent,
//            @RequestParam(value = "applyImage", required = false) MultipartFile applyImage) throws IOException {
//
//        String applyImage_url = null;
//
//        if (applyImage != null) {
//            applyImage_url = imageUploadService.uploadImage(applyImage);
//        }
//        ApplyInfo applyInfo = applyInfoRepository.findById(jobPostId).orElseThrow(RuntimeException::new);
//        System.out.println("Before P: " + applyInfo.getApplyImage());
//        String after = applyInfo.getApplyImage();
//
//        applyInfo.setApplyContent(applyContent);
//
//        if (applyImage != null) {
//            applyInfo.setApplyImage(applyImage_url);
//        }
//
//        System.out.println("After P: " + applyInfo.getApplyImage());
//
//        applyInfoRepository.save(applyInfo);
//
//        ResponseDTO<Void> dto = new ResponseDTO<>();
//        dto.setStatus(200);
//        dto.setMessage("apply info created successfully");
//
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .header("Location", "/applyInfo")
//                .body(dto);
//    }

    @PostMapping("/registry")
    public ResponseEntity<ResponseDTO<Void>> createOrUpdateApplyInfo(
            @RequestParam("jobPostId") Long jobPostId,
            @RequestParam("userId") Long userId,
            @RequestParam("applyContent") String applyContent,
            @RequestParam(value = "applyImage", required = false) MultipartFile applyImage) throws IOException {

        // Kiểm tra nếu tham số jobPostId và userId là null
        if (jobPostId == null || userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO<>(null, "jobPostId và userId không được để trống", 400));
        }

        String applyImageUrl = null;

        // Nếu có ảnh thì tải lên
        if (applyImage != null) {
            applyImageUrl = imageUploadService.uploadImage(applyImage);
        }

        // Kiểm tra JobPost và User
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy JobPost với id " + jobPostId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User với id " + userId));

        // Cập nhật ApplyInfo
        ApplyInfo applyInfo = applyInfoRepository.findByJobPostIdAndUserId(jobPostId, userId)
                .orElse(new ApplyInfo());  // Tạo mới nếu không tìm thấy ApplyInfo

        applyInfo.setJobPost(jobPost);
        applyInfo.setUser(user);
        applyInfo.setApplyContent(applyContent);
        if (applyImageUrl != null) {
            applyInfo.setApplyImage(applyImageUrl);
        }

        applyInfoRepository.save(applyInfo);

        // Phản hồi thành công
        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        responseDTO.setStatus(200);
        responseDTO.setMessage("ApplyInfo đã được tạo hoặc cập nhật thành công");

        return ResponseEntity.ok(responseDTO);
    }






}








