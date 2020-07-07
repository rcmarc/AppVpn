package com.github.rcmarc.appvpn.services;

import com.github.rcmarc.appvpn.error.VpnException;
import com.github.rcmarc.appvpn.models.RefreshToken;
import com.github.rcmarc.appvpn.repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository tokenRepository;

    @Transactional
    public RefreshToken generateRefreshToken(){
        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setCreatedDate(LocalDateTime.now());
        return tokenRepository.save(token);
    }

    @Transactional
    public void validateRefreshToken(String token){
        tokenRepository.findByToken(token)
                .orElseThrow(() -> new VpnException("Invalid refresh token"));
        tokenRepository.deleteByToken(token);
    }

}
