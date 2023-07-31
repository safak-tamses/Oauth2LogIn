package com.example.test.Controller;

import com.example.test.Model.ResponseOrRequest.AuthenticationRequest;
import com.example.test.Model.ResponseOrRequest.AuthenticationResponse;
import com.example.test.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class ApiController {
    private final UserService userService;


    @GetMapping("/oauth")
    public String test(){
        return "test";
    }
    @GetMapping("logGoogle")
    public String t(){
        return "s";
    }
    @GetMapping("/dashboard")
    public String x(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uname = authentication.getName();
        Boolean x = authentication.isAuthenticated();



        return "Hello " + authentication.getName() + " welcome";
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
