package com.example.test.Service;

import com.example.test.Model.Enum.Role;
import com.example.test.Model.User;
import com.example.test.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultStart {
    private final UserRepository userRepository;

    public void createDefaultUser() {
        User Admin = new User();
        Admin.setEmail("admin@admin.com");
        Admin.setPassword("$2a$10$puwheC5Hl2EY7fSeZeIlX.QiIUOtWcFlbl2LsI04dY1Dd70MwYlIe");
        Admin.setRole(Role.valueOf("ROLE_ADMIN"));
        if (userRepository.findByEmail(Admin.getEmail()).isEmpty()) {
            userRepository.save(Admin);
        }
    }
}