package khuong.com.webmakeupconnection.service;

import khuong.com.webmakeupconnection.dto.UserDTO;
import khuong.com.webmakeupconnection.entity.User;
import khuong.com.webmakeupconnection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    private List<UserDTO> mapToDto(List<User> listEntity) {
        List<UserDTO> list = new ArrayList<>();
        for (User user : listEntity) {
            UserDTO dto = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getRole(), user.getCccd(), user.getPortraitPhoto(), user.getCreatedAt());
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
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setRole(userDTO.getRole());
        user.setCccd(userDTO.getCccd());
        user.setPortraitPhoto(userDTO.getPortraitPhoto());
        user.setCreatedAt(userDTO.getCreatedAt());
        userRepo.save(user);
    }

    public UserDTO getById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        if (user == null) {
            throw new RuntimeException("Khong tim thay nguoi dung");
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getRole(), user.getCccd(), user.getPortraitPhoto(), user.getCreatedAt());
    }

    public void delete(Long id) {
        userRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        userRepo.deleteById(id);
    }
}

