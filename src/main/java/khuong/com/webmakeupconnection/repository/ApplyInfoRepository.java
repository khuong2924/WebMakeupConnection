package khuong.com.webmakeupconnection.repository;

import khuong.com.webmakeupconnection.entity.ApplyInfo;
import khuong.com.webmakeupconnection.entity.JobPost;
import khuong.com.webmakeupconnection.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplyInfoRepository extends JpaRepository<ApplyInfo, Long> {

    @Query("SELECT a FROM ApplyInfo a WHERE a.jobPost.id = :jobPostId AND a.user.id = :userId")
    Optional<ApplyInfo> findByJobPostIdAndUserId(@Param("jobPostId") Long jobPostId, @Param("userId") Long userId);
}
