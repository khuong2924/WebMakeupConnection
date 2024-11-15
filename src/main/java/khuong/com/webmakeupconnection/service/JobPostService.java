package khuong.com.webmakeupconnection.service;

import khuong.com.webmakeupconnection.dto.JobPostDTO;
import khuong.com.webmakeupconnection.entity.JobPost;
import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.repository.JobPostRepository;
import khuong.com.webmakeupconnection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;


    private List<JobPostDTO> mapToDto(List<JobPost> jobPosts) {
        List<JobPostDTO> jobPostDTOList = new ArrayList<>();
        for (JobPost jobPost : jobPosts) {
            jobPostDTOList.add(new JobPostDTO(
                    jobPost.getId(),
                    jobPost.getUser().getId(),
                    jobPost.getTitle(),
                    jobPost.getDescription(),
                    jobPost.getJobType(),
                    jobPost.getLocation(),
                    jobPost.getSalary(),
                    jobPost.getPostPhoto(),
                    jobPost.getStatus(),
                    jobPost.getCreatedAt()
            ));
        }
        return jobPostDTOList;
    }

    public List<JobPostDTO> getAll() {
        List<JobPost> jobPosts = jobPostRepository.findAll();
        return mapToDto(jobPosts);
    }

    public void create(JobPostDTO jobPostDTO) {
        JobPost jobPost = new JobPost();
        jobPost.setTitle(jobPostDTO.getTitle());
        jobPost.setDescription(jobPostDTO.getDescription());
        jobPost.setJobType(jobPostDTO.getJobType());
        jobPost.setLocation(jobPostDTO.getLocation());
        jobPost.setSalary(jobPostDTO.getSalary());
        jobPost.setPostPhoto(jobPostDTO.getPostPhoto());
        jobPost.setStatus(jobPostDTO.getStatus());
        jobPostRepository.save(jobPost);
    }

    public void update(Long id, JobPostDTO jobPostDTO) {
        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job post not found"));
        jobPost.setTitle(jobPostDTO.getTitle());
        jobPost.setDescription(jobPostDTO.getDescription());
        jobPost.setJobType(jobPostDTO.getJobType());
        jobPost.setLocation(jobPostDTO.getLocation());
        jobPost.setSalary(jobPostDTO.getSalary());
        jobPost.setPostPhoto(jobPostDTO.getPostPhoto());
        jobPost.setStatus(jobPostDTO.getStatus());
        jobPostRepository.save(jobPost);
    }

    public JobPostDTO getById(Long id) {
        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job post not found"));
        return new JobPostDTO(
                jobPost.getId(),
                jobPost.getUser().getId(),
                jobPost.getTitle(),
                jobPost.getDescription(),
                jobPost.getJobType(),
                jobPost.getLocation(),
                jobPost.getSalary(),
                jobPost.getPostPhoto(),
                jobPost.getStatus(),
                jobPost.getCreatedAt()
        );
    }

    public void delete(Long id) {
        jobPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job post not found"));
        jobPostRepository.deleteById(id);
    }

    public List<JobPost> getByUserId(Long userId) {
        return jobPostRepository.findByUserId(userId);
    }


    public List<JobPost> searchJobPosts(String jobType, LocalDateTime date, BigDecimal startSalary, BigDecimal endSalary) {
        return jobPostRepository.searchJobPosts(jobType, date, startSalary, endSalary);
    }
}
