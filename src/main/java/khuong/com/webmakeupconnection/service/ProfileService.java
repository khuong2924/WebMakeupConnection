package khuong.com.webmakeupconnection.service;

import jakarta.servlet.http.HttpSession;
import khuong.com.webmakeupconnection.dto.ProfileDTO;
import khuong.com.webmakeupconnection.entity.Profile;
import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.repository.ProfileRepository;
import khuong.com.webmakeupconnection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/profile")
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;


//    private List<ProfileDTO> mapToDto(List<Profile> profileList) {
//        List<ProfileDTO> profileDTOList = new ArrayList<>();
//        for (Profile profile : profileList) {
//            profileDTOList.add(new ProfileDTO(
//                    profile.getId(),
//                    profile.getUserId(),
//                    profile.getFullName(),
//                    profile.getBirthDate(),
//                    profile.getGender(),
//                    profile.getBio(),
//                    profile.getAddress(),
//                    profile.getPortfolioPhoto(),
//                    profile.getCoverPhoto()
//            ));
//        }
//        return profileDTOList;
//    }

    public void createOrUpdate(ProfileDTO profileDTO) {
        Profile profile = profileRepository.findByUserId(profileDTO.getUser_id())
                .orElse(new Profile());

        profile.setUser_id(profileDTO.getUser_id());
        profile.setFullName(profileDTO.getFullName());
        profile.setBirthDate(profileDTO.getBirthDate());
        profile.setGender(profileDTO.getGender());
        profile.setBio(profileDTO.getBio());
        profile.setAddress(profileDTO.getAddress());
        profile.setPortfolioPhoto(profileDTO.getPortfolioPhoto());
        profile.setCoverPhoto(profileDTO.getCoverPhoto());

        profileRepository.save(profile);
    }


    public List<ProfileDTO> mapToDto(List<Profile> profileList) {
        List<ProfileDTO> profileDTOList = new ArrayList<>();
        for (Profile profile : profileList) {
            profileDTOList.add(new ProfileDTO(
                    profile.getId(),
                    profile.getUser_id(),
                    profile.getFullName(),
                    profile.getBirthDate(),
                    profile.getGender(),
                    profile.getBio(),
                    profile.getAddress(),
                    profile.getPortfolioPhoto(),
                    profile.getCoverPhoto(),
                    profile.getUser().getRole()
            ));
        }
        return profileDTOList;
    }

    public List<ProfileDTO> getAll() {
        List<Profile> profiles = profileRepository.findAll();
        return mapToDto(profiles);
    }

    public void create(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setUser_id(profileDTO.getUser_id());
        profile.setFullName(profileDTO.getFullName());
        profile.setBirthDate(profileDTO.getBirthDate());
        profile.setGender(profileDTO.getGender());
        profile.setBio(profileDTO.getBio());
        profile.setAddress(profileDTO.getAddress());
        profile.setPortfolioPhoto(profileDTO.getPortfolioPhoto());
        profile.setCoverPhoto(profileDTO.getCoverPhoto());
        profileRepository.save(profile);
    }

    public void update(Long id, ProfileDTO profileDTO) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setUser_id(profileDTO.getUser_id());
        profile.setFullName(profileDTO.getFullName());
        profile.setBirthDate(profileDTO.getBirthDate());
        profile.setGender(profileDTO.getGender());
        profile.setBio(profileDTO.getBio());
        profile.setAddress(profileDTO.getAddress());
        profile.setPortfolioPhoto(profileDTO.getPortfolioPhoto());
        profile.setCoverPhoto(profileDTO.getCoverPhoto());
        profileRepository.save(profile);
    }

    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found for user ID: " + userId));
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("profile") ProfileDTO profileDTO, HttpSession session, Model model) {
        // Lấy username từ session
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/login";
        }

        // Tìm người dùng dựa vào username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // Tìm profile dựa vào user ID
        Profile profile = getProfileByUserId(user.getId()); // Sử dụng phương thức này mà không cần inject ProfileService
        if (profile == null) {
            model.addAttribute("error", "Không tìm thấy profile");
            return "error";
        }

        // Cập nhật thông tin profile từ profileDTO
        profile.setFullName(profileDTO.getFullName());
        profile.setBirthDate(profileDTO.getBirthDate());
        profile.setGender(profileDTO.getGender());
        profile.setBio(profileDTO.getBio());
        profile.setAddress(profileDTO.getAddress());
        profile.setPortfolioPhoto(profileDTO.getPortfolioPhoto());
        profile.setCoverPhoto(profileDTO.getCoverPhoto());

        // Lưu lại profile đã cập nhật
        update(profile.getId(), profileDTO); // Gọi phương thức update trực tiếp

        return "redirect:/profile";  // Chuyển hướng về trang profile sau khi cập nhật thành công
    }

    public ProfileDTO getById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        if (profile == null) {
            throw new RuntimeException("Profile not found");
        }

        return new ProfileDTO(
                profile.getId(),
                profile.getUser_id(),
                profile.getFullName(),
                profile.getBirthDate(),
                profile.getGender(),
                profile.getBio(),
                profile.getAddress(),
                profile.getPortfolioPhoto(),
                profile.getCoverPhoto(),
                profile.getUser().getRole()
        );
    }

    public ProfileDTO getByUserId(Long id) {
        Profile profile = profileRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        if (profile == null) {
            throw new RuntimeException("Profile not found");
        }

        return new ProfileDTO(
                profile.getId(),
                profile.getUser_id(),
                profile.getFullName(),
                profile.getBirthDate(),
                profile.getGender(),
                profile.getBio(),
                profile.getAddress(),
                profile.getPortfolioPhoto(),
                profile.getCoverPhoto(),
                profile.getUser().getRole()
        );
    }

    public void delete(Long id) {
        profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profileRepository.deleteById(id);
    }
}
