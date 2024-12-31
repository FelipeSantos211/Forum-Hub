package com.felipesantos.forumhub.controller;

import com.felipesantos.forumhub.dto.AuthResponse;
import com.felipesantos.forumhub.dto.MessageResponse;
import com.felipesantos.forumhub.util.JwtUtil;
import com.felipesantos.forumhub.dto.LoginRequest;
import com.felipesantos.forumhub.model.Users;
import com.felipesantos.forumhub.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody Users users) {
        if (userRepository.existsByEmail(users.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Esse email ja foi cadastrado!"));
        }

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        userRepository.save(users);
        return ResponseEntity.ok(new MessageResponse("Usuário registrado com sucesso!"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        String token = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}