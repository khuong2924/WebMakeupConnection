package khuong.com.webmakeupconnection.controller;

import khuong.com.webmakeupconnection.config.SessionUtils;
import khuong.com.webmakeupconnection.dto.ProfileDTO;
import khuong.com.webmakeupconnection.entity.JobPost;
import khuong.com.webmakeupconnection.repository.JobPostRepository;
import khuong.com.webmakeupconnection.service.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ProfileService profileService;
    private final JobPostRepository jobPostRepository;

    public HomeController(ProfileService profileService, JobPostRepository jobPostRepository) {
        this.profileService = profileService;
        this.jobPostRepository = jobPostRepository;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<JobPost> jobPosts = jobPostRepository.findAll();
        model.addAttribute("jobPosts", jobPosts);
        return "homePage";
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        Long userId = SessionUtils.getCurrentUserId();
        ProfileDTO profileDTO = profileService.getByUserId(userId);
        System.out.println(profileDTO.getPortfolioPhoto());
        System.out.println(profileDTO.getCoverPhoto());
        model.addAttribute("profile", profileDTO);

        List<JobPost> jobPosts = jobPostRepository.findByUserId(userId);
        model.addAttribute("jobPosts", jobPosts);
        return "profile";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signUp";
    }

    @GetMapping("/chatBox")
    public String showChatBoxPage() {
        return "chatBox";
    }

    @GetMapping("/account")
    public String showAccountPage() {
        return "account";
    }
}
