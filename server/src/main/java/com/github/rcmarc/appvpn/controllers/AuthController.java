package com.github.rcmarc.appvpn.controllers;

import com.github.rcmarc.appvpn.data.*;
import com.github.rcmarc.appvpn.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth/")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("sign")
    public ResponseEntity<String> sign(@RequestBody RegisterRequest request){
        authService.sign(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        try{
            return ResponseEntity.ok(authService.login(request));
        }catch (AuthenticationException ignored) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthenticationResponse());
        }
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authService.sign(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("token")
    public ResponseEntity<AuthenticationResponse> token(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest request) {
        authService.logout(request.getJwt());
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok().build();
    }
}
