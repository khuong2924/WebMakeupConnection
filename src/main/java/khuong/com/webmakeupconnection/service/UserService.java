package khuong.com.webmakeupconnection.service;

import khuong.com.webmakeupconnection.dto.UserDTO;
import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public User createUser(User user) {
        return userRepo.save(user);
    }

    private List<UserDTO> mapToDto(List<User> listEntity) {
        List<UserDTO> list = new ArrayList<>();
        for (User user : listEntity) {
            UserDTO dto = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getPhone(), user.getRole(), user.getCccd(), user.getPortraitPhoto(), user.getCreatedAt());
            list.add(dto);
        }
        return list;
    }

    public List<UserDTO> getAll() {
        List<User> users = userRepo.findAll();
        return mapToDto(users);
    }

    public void create(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setRole(userDTO.getRole());
        user.setCccd(userDTO.getCccd());
        user.setPortraitPhoto(userDTO.getPortraitPhoto());
        user.setCreatedAt(userDTO.getCreatedAt());
        userRepo.save(user);
    }

    public void update(Long id, UserDTO userDTO) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setRole(userDTO.getRole());
        user.setCccd(userDTO.getCccd());
        user.setPortraitPhoto(userDTO.getPortraitPhoto());
        user.setCreatedAt(userDTO.getCreatedAt());
        userRepo.save(user);
    }

    public UserDTO getById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(), user.getRole(), user.getCccd(), user.getPortraitPhoto(), user.getCreatedAt());
    }

    public void delete(Long id) {
        userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepo.deleteById(id);
    }

    public boolean validateUser(String username, String password) {
        Optional<User> optionalUser = userRepo.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }



}

