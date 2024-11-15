package khuong.com.webmakeupconnection.controller;

import jakarta.servlet.http.HttpSession;
import khuong.com.webmakeupconnection.config.SessionUtils;
import khuong.com.webmakeupconnection.dto.ProfileDTO;
import khuong.com.webmakeupconnection.entity.JobPost;
import khuong.com.webmakeupconnection.entity.Notification;
import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.repository.JobPostRepository;
import khuong.com.webmakeupconnection.repository.NotificationRepository;
import khuong.com.webmakeupconnection.repository.UserRepository;
import khuong.com.webmakeupconnection.service.JobPostService;
import khuong.com.webmakeupconnection.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class HomeController {

    private final ProfileService profileService;
    private final JobPostRepository jobPostRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final JobPostService jobPostService;

    public HomeController(ProfileService profileService, JobPostRepository jobPostRepository, NotificationRepository notificationRepository, UserRepository userRepository, JobPostService jobPostService) {
        this.profileService = profileService;
        this.jobPostRepository = jobPostRepository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.jobPostService = jobPostService;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<JobPost> jobPosts = jobPostRepository.findAll();

        // Sort the list based on the createdAt field
        jobPosts.sort((jobPost1, jobPost2) -> jobPost2.getCreatedAt().compareTo(jobPost1.getCreatedAt()));

        // Add the sorted list to the model
        model.addAttribute("jobPosts", jobPosts);
        return "homePage";
    }

    @GetMapping("/search")
    public String searchJobPosts(
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String date,  // Receive date as String
            @RequestParam(required = false) BigDecimal startSalary,
            @RequestParam(required = false) BigDecimal endSalary,
            Model model) {

        if (jobType != null && jobType.equals("")) {
            jobType = null;
        }

        System.out.println("Job Type: " + jobType);

        LocalDateTime localDateTime = null;
        if (date != null && !date.isEmpty()) {
            date += "T00:00:00";
            localDateTime = LocalDateTime.parse(date);
        }


        List<JobPost> jobPosts = jobPostService.searchJobPosts(jobType, localDateTime, startSalary, endSalary);

        jobPosts.sort((jobPost1, jobPost2) -> jobPost2.getCreatedAt().compareTo(jobPost1.getCreatedAt()));

        model.addAttribute("jobPosts", jobPosts);

        return "homePage";
    }


    @PostMapping("/create")
    public String createJobPost(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("time") String time,  // String format for time
            @RequestParam("style") String style,
            @RequestParam("location") String location,
            @RequestParam("cost") Double cost,
            @RequestParam(value = "file", required = false) MultipartFile file,
            RedirectAttributes redirectAttributes) {

        try {
            // If time string is only a date, append the default time (00:00)
            if (time.length() == 10) {  // Check if the string has only the date part (yyyy-MM-dd)
                time += "T00:00";  // Append default time to create a valid LocalDateTime
            }

            // Convert the time string to LocalDateTime
            JobPost jobPost = new JobPost();
            jobPost.setTitle(title);
            jobPost.setDescription(description);
            jobPost.setStart(LocalDateTime.parse(time));  // Parse the LocalDateTime with date and time
            jobPost.setLocation(location);
            jobPost.setSalary(BigDecimal.valueOf(cost));
            jobPost.setCreatedAt(LocalDateTime.now());

            if(file != null) {
                jobPost.setPostPhoto(imageUploadService.uploadImage(file));
                System.out.println(1);
            }
            Long userId = SessionUtils.getCurrentUserId();

            User user = userRepository.findById(userId).orElse(null);
            jobPost.setUser(user);
            JobPost post = jobPostRepository.save(jobPost);
            System.out.println(post);
            redirectAttributes.addFlashAttribute("message", "Job post created successfully!");
            return "redirect:/home";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error creating job post.");
            return "redirect:/home";
        }
    }


    @PostMapping("/follow")
    public String followJobPost(@RequestParam("jobPostId") Long jobPostId, HttpSession session) {
        Long userId = SessionUtils.getCurrentUserId();

        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            JobPost jobPost = jobPostRepository.findById(jobPostId).orElse(null);

            if (jobPost != null) {
                Optional<Notification> existingNotification = notificationRepository
                        .findByUserAndJobPost(user, jobPost);

                if (!existingNotification.isPresent()) {
                    Notification notification = new Notification();
                    notification.setUser(user);
                    notification.setJobPost(jobPost);
                    notificationRepository.save(notification);
                } else {
                    System.out.println("User has already followed this job post.");
                }
            }
        }
        return "redirect:/schedule";
    }

    @PostMapping("/flagNotification")
    public String flagNotification(@RequestParam("notificationId") Long notificationId, RedirectAttributes redirectAttributes) {
        try {
            Notification notification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

            notification.setFlagged(!notification.isFlagged());  // Toggle flag state
            notificationRepository.save(notification);

            // Add success message
            redirectAttributes.addFlashAttribute("message", "Notification flagged successfully!");
            return "redirect:/schedule";  // Redirect to your notification list page

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error flagging notification.");
            return "redirect:/schedule";
        }
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

    @Autowired
    private ImageUploadService imageUploadService;

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

    @GetMapping("/schedule")
    public String showSchedulePage(@RequestParam(name = "date", required = false) String date, Model model) {
        Long userId = SessionUtils.getCurrentUserId();

        LocalDate selectedDate = (date != null) ? LocalDate.parse(date) : null;

        List<Notification> notifications;
        Set<LocalDate> notificationDates = new HashSet<>();

        if (selectedDate != null) {
            notifications = notificationRepository.findByJobPostStartDateAndUserId(selectedDate, userId);
        } else {
            notifications = notificationRepository.findAllByUserId(userId);
        }

        for (Notification notification : notifications) {
            JobPost jobPost = notification.getJobPost();
            if (jobPost.getStart() != null) {
                LocalDate jobPostStartDate = jobPost.getStart().toLocalDate();
                notificationDates.add(jobPostStartDate);
            }
        }

        notifications.sort(Comparator.comparing(Notification::isFlagged).reversed()
                .thenComparing(notification -> notification.getJobPost().getStart()));

        System.out.println("notificationDates: " + notificationDates);

        model.addAttribute("notifications", notifications);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("notificationDates", new ArrayList<>(notificationDates));

        return "schedules";
    }

}
