package com.example.test.Controller;

import com.example.test.Model.ResponseOrRequest.AuthenticationRequest;
import com.example.test.Model.ResponseOrRequest.AuthenticationResponse;
import com.example.test.Model.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import com.example.test.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class ApiController {
    private final UserService userService;


    @GetMapping("/login/google")
    public Map<String,Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }

    @GetMapping("/register/google")
    public RedirectView googleRegister(@AuthenticationPrincipal DefaultOAuth2User oauth2User) {

        return new RedirectView("/home");
    }
    @GetMapping("/home")
    public ResponseEntity home(){
        return ResponseEntity.ok("Hello");
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
