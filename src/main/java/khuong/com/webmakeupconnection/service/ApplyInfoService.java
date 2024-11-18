package khuong.com.webmakeupconnection.service;

import khuong.com.webmakeupconnection.dto.ApplyInfoDTO;
import khuong.com.webmakeupconnection.entity.ApplyInfo;
import khuong.com.webmakeupconnection.entity.JobPost;
import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.repository.ApplyInfoRepository;
import khuong.com.webmakeupconnection.repository.JobPostRepository;
import khuong.com.webmakeupconnection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplyInfoService {

    @Autowired
    private ApplyInfoRepository applyInfoRepository;

    @Autowired
    private JobPostRepository jobPostRepository;  // Thêm JobPostRepository
    @Autowired
    private UserRepository userRepository;  // Thêm UserRepository

    private List<ApplyInfoDTO> mapToDTO(List<ApplyInfo> applyInfoList) {
        List<ApplyInfoDTO> dtoList = new ArrayList<ApplyInfoDTO>();
        for (ApplyInfo applyInfo : applyInfoList) {
            dtoList.add(new ApplyInfoDTO(
                    applyInfo.getId(),
                    applyInfo.getJobPost().getId(),
                    applyInfo.getUser().getId(),
                    applyInfo.getApplyContent(),
                    applyInfo.getApplyImage()
            ));
        }
        return dtoList;
    }

    public List<ApplyInfoDTO> getAll() {
        return mapToDTO(applyInfoRepository.findAll());
    }

    public ApplyInfoDTO getById(Long id) {
        ApplyInfo applyInfo = applyInfoRepository.findById(id).orElse(null);

        return new ApplyInfoDTO(
                applyInfo.getId(),
                applyInfo.getJobPost().getId(),
                applyInfo.getUser().getId(),
                applyInfo.getApplyContent(),
                applyInfo.getApplyImage()
        );
    }

    public void create(ApplyInfoDTO applyInfoDTO) {
        ApplyInfo applyInfo = new ApplyInfo();
        applyInfo.setJobPost(jobPostRepository.findById(applyInfoDTO.getJobPostId()).orElse(null));
        applyInfo.setUser(userRepository.findById(applyInfoDTO.getUserId()).orElse(null));
        applyInfo.setApplyContent(applyInfoDTO.getApplyContent());
        applyInfo.setApplyImage(applyInfoDTO.getApplyImage());

        applyInfoRepository.save(applyInfo);
    }

    public void update(Long id, ApplyInfoDTO applyInfoDTO) {
        ApplyInfo applyInfo = applyInfoRepository.findById(id).orElse(null);

        if (applyInfo == null) {
            throw new RuntimeException("Không tìm thấy ApplyInfo với id " + id);
        }

        // Cập nhật JobPost và User nếu có thay đổi
        JobPost jobPost = jobPostRepository.findById(applyInfoDTO.getJobPostId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy JobPost với id " + applyInfoDTO.getJobPostId()));
        User user = userRepository.findById(applyInfoDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User với id " + applyInfoDTO.getUserId()));

        applyInfo.setJobPost(jobPost);
        applyInfo.setUser(user);
        applyInfo.setApplyContent(applyInfoDTO.getApplyContent());
        applyInfo.setApplyImage(applyInfoDTO.getApplyImage());

        applyInfoRepository.save(applyInfo);
    }

    public void delete(Long id) {
        ApplyInfo applyInfo = applyInfoRepository.findById(id).orElse(null);
        if (applyInfo != null) {
            applyInfoRepository.delete(applyInfo);
        }
    }

}











