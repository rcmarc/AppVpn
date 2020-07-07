package com.github.rcmarc.appvpn.services;

import com.github.rcmarc.appvpn.data.AuthenticationRequest;
import com.github.rcmarc.appvpn.data.AuthenticationResponse;
import com.github.rcmarc.appvpn.data.RefreshTokenRequest;
import com.github.rcmarc.appvpn.data.RegisterRequest;
import com.github.rcmarc.appvpn.models.Admin;
import com.github.rcmarc.appvpn.models.User;
import com.github.rcmarc.appvpn.repositories.AdminRepository;
import com.github.rcmarc.appvpn.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final VerificationTokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final AppVpnUserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @Transactional
    public void sign(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdDate(LocalDateTime.now())
                .enabled(false).build();
        userRepository.save(user);
        if (request.isAdmin()) {
            final Admin admin = Admin.builder().user(user).build();
            adminRepository.save(admin);
        }
        tokenService.generateVerificationToken(user);
    }

    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwt, refreshTokenService.generateRefreshToken().getToken());
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        refreshTokenService.validateRefreshToken(request.getRefresh());
        String newToken = jwtService.generateToken(userDetailsService.loadUserByUsername(request.getUsername()));
        return AuthenticationResponse.builder()
                .jwt(newToken)
                .refresh(refreshTokenService.generateRefreshToken().getToken())
                .build();
    }

    public void logout(String jwt) {
        jwtService.removeToken(jwt);
    }
}
