package khuong.com.webmakeupconnection.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyInfoDTO {
    private Long id;
    private Long jobPostId;
    private Long userId;
    private String applyContent;
    private String applyImage;
}
