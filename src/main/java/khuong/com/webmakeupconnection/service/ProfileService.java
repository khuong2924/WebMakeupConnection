//package khuong.com.webmakeupconnection.service;
//
//import khuong.com.webmakeupconnection.dto.ProfileDTO;
//import khuong.com.webmakeupconnection.entity.Profile;
//import khuong.com.webmakeupconnection.entity.User;
//import khuong.com.webmakeupconnection.repository.ProfileRepository;
//import khuong.com.webmakeupconnection.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ProfileService {
//
//    @Autowired
//    private ProfileRepository profileRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private List<ProfileDTO> mapToDto(List<Profile> profileList) {
//        List<ProfileDTO> profileDTOList = new ArrayList<>();
//        for (Profile profile : profileList) {
//            profileDTOList.add(new ProfileDTO(
//                    profile.getId(),
//                    profile.getFullName(),
//                    profile.getBirthDate(),
//                    profile.getGender(),
//                    profile.getBio(),
//                    profile.getSkills(),
//                    profile.getExperience(),
//                    profile.getPortfolioPhoto()
//            ));
//        }
//        return profileDTOList;
//    }
//
//    public List<ProfileDTO> getAll() {
//        List<Profile> profiles = profileRepository.findAll();
//        return mapToDto(profiles);
//    }
//
//    public void create(ProfileDTO profileDTO) {
//        Profile profile = new Profile();
//        User user = userRepository.findById(profileDTO.getId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        profile.setUser(user);
//        profile.setFullName(profileDTO.getFullName());
//        profile.setBirthDate(profileDTO.getBirthDate());
//        profile.setGender(profileDTO.getGender());
//        profile.setBio(profileDTO.getBio());
//        profile.setSkills(profileDTO.getSkills());
//        profile.setExperience(profileDTO.getExperience());
//        profile.setPortfolioPhoto(profileDTO.getPortfolioPhoto());
//        profileRepository.save(profile);
//    }
//
//    public void update(Long id, ProfileDTO profileDTO) {
//        Profile profile = profileRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Profile not found"));
//        profile.setFullName(profileDTO.getFullName());
//        profile.setBirthDate(profileDTO.getBirthDate());
//        profile.setGender(profileDTO.getGender());
//        profile.setBio(profileDTO.getBio());
//        profile.setSkills(profileDTO.getSkills());
//        profile.setExperience(profileDTO.getExperience());
//        profile.setPortfolioPhoto(profileDTO.getPortfolioPhoto());
//        profileRepository.save(profile);
//    }
//
//    public ProfileDTO getById(Long id) {
//        Profile profile = profileRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Profile not found"));
//        return new ProfileDTO(
//                profile.getId(),
//                profile.getFullName(),
//                profile.getBirthDate(),
//                profile.getGender(),
//                profile.getBio(),
//                profile.getSkills(),
//                profile.getExperience(),
//                profile.getPortfolioPhoto()
//        );
//    }
//
//    public void delete(Long id) {
//        profileRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Profile not found"));
//        profileRepository.deleteById(id);
//    }
//}
