package me.pravat.uShort.controller;

import lombok.RequiredArgsConstructor;
import me.pravat.uShort.dto.AuthRequest;
import me.pravat.uShort.dto.JwtResponse;
import me.pravat.uShort.entity.User;
import me.pravat.uShort.service.JwtService;
import me.pravat.uShort.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/register")
    public String addNewUser(@RequestBody User user) {
        return service.addUser(user);
    }

    // Removed the role checks here as they are already managed in SecurityConfig

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            var response = jwtService.generateToken(authRequest.getEmail());
            return ResponseEntity.ok(new JwtResponse(response));
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}