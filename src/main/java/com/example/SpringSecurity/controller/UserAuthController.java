package com.example.SpringSecurity.controller;


import com.example.SpringSecurity.entity.UserAuthEntity;
import com.example.SpringSecurity.service.UserAuthEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private UserAuthEntityService userAuthEntityService;

    private PasswordEncoder encoder;

    public UserAuthController(PasswordEncoder passwordEncoder, UserAuthEntityService userAuthEntityService) {
        this.encoder = passwordEncoder;
        this.userAuthEntityService = userAuthEntityService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloApi(){
        return ResponseEntity.ok("Hello Shashank");
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserAuthEntity user){// why we use @RequestBody?


        System.out.println("encoder1: "+encoder.hashCode());
        // save this user
        String encoded = encoder.encode(user.getPassword());
        System.out.println("encoded1: "+encoded);
        user.setPassword(encoder.encode(user.getPassword()));

        userAuthEntityService.save(user);

        return ResponseEntity.ok("registered");

    }
}
