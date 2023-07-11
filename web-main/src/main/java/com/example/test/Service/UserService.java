package com.example.test.Service;

import com.example.test.Model.ResponseOrRequest.AuthenticationRequest;
import com.example.test.Model.ResponseOrRequest.AuthenticationResponse;
import com.example.test.Model.DTO.UserDTO;
import com.example.test.Model.Enum.Role;
import com.example.test.Model.User;
import com.example.test.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User findUserToToken(String bearerToken) throws Exception {
        try {
            bearerToken = bearerToken.substring(7);
            final String userName = jwtService.extractUsername(bearerToken);
            User user = userRepository.findByEmail(userName).orElseThrow(() -> new Exception("Kullanıcı bulunamadı "));
            return user;
        } catch (Exception e) {
            throw new Exception("Token problem");
        }
    }

    public String userCreate(AuthenticationRequest request) {
        var temp = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_NORMAL_USER)
                .build();
        userRepository.save(temp);
        return "User created successfully";
    }

    public AuthenticationResponse userLogin(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        String privateKey;
        if (user.getRole() == Role.ROLE_NORMAL_USER) {
            privateKey = "ThWmZq4t7w!z%C&F)J@NcRfUjXn2r5u8x/A?D(G-KaPdSgVkYp3s6v9y$B&E)H@MbQeThWmZq4t7w!z%C*F-JaNdRfUjXn2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgUkXp2s5u8x/A?D(G+KbPeShVmYq3t6w9y$B&E)H@McQfTjWnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@McQfTjWnZr4u7x!A%D*G-KaPdRgUkXp2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdSgVkYp3s5v8y/B?E(H+MbQeThWmZq4t7w9z$C&F)J@NcRfUjXn2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRfUjXn2r5u8x/A?D(G+KbPeSgVkYp3s6v9y$B&E)H@McQ";

        } else {
            privateKey = "t7w!z%C*F-JaNdRfUjXn2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgUkXp2s5u8x/A?D(G+KbPeShVmYq3t6w9y$B&E)H@McQfTjWnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@McQfTjWnZr4u7x!A%D*G-KaPdRgUkXp2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdSgVkYp3s5v8y/B?E(H+MbQeThWmZq4t7w9z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)H+MbQeThWmZq4t7w!z%C*F-JaNcRfUjXn2r5u8x/A?D(G+KbPeSgVkYp3s6v9y$B&E)H@McQfTjWmZq4t7w!z%C*F-JaNdRgUkXp2r5u8x/A?D(G+KbPeShVmYq3t6v9y$B&E)H@McQfTjWnZr";
        }
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .roleToken(privateKey)
                .success(true)
                .message("Hello " + user.getUsername())
                .build();
    }


    // TODO: 23.06.2023 burası kontrol edilmeli gelen şifre ve tokenden alınan şifre uyuşmuyor kontrol edilmeli 
    public ResponseEntity resetPassword(String token, String password, String newPassword) throws Exception {
        try {
            User user = findUserToToken(token);
            System.out.println(user.getPassword());
            System.out.println(passwordEncoder.encode(password));
            if (user.getPassword().equals(passwordEncoder.encode(password))) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.ok("Incorrect password");
            }
        } catch (Exception e) {
            throw new Exception("Failed to reset password. Possible reasons: user not found or an unexpected error occurred.");
        }
    }

}

