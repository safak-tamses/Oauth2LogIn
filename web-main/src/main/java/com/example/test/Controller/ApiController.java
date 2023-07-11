package com.example.test.Controller;

import com.example.test.Model.ResponseOrRequest.AuthenticationRequest;
import com.example.test.Model.ResponseOrRequest.AuthenticationResponse;
import com.example.test.Model.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.test.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class ApiController {
    private final UserService userService;
    @GetMapping("/oauth")
    public Map<String, Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(userService.userLogin(request));
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.userCreate(request));
    }


    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestHeader("Authorization") String token, @RequestParam("password") String password, @RequestParam("newPassword") String resetpass) throws Exception {
        return userService.resetPassword(token, password, resetpass);
    }



}
