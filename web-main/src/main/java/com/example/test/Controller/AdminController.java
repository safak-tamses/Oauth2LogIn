package com.example.test.Controller;


import com.example.test.Service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/ad")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;


}
