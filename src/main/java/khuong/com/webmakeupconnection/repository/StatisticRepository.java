package khuong.com.webmakeupconnection.repository;

import khuong.com.webmakeupconnection.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    List<Statistic> findByUserId(Long userId);
}
