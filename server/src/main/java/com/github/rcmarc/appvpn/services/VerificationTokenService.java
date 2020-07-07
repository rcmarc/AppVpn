package com.github.rcmarc.appvpn.services;

import com.github.rcmarc.appvpn.models.User;
import com.github.rcmarc.appvpn.models.VerificationToken;
import com.github.rcmarc.appvpn.repositories.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    public String generateVerificationToken(User user){
        String s = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(s)
                .user(user)
                .build();

        verificationTokenRepository.save(verificationToken);
        return s;
    }

}
