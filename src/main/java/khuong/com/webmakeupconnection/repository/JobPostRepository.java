package khuong.com.webmakeupconnection.repository;

import khuong.com.webmakeupconnection.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findByUserId(Long userId);
}
