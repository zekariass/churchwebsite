package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.PasswordResetToken;
import com.churchwebsite.churchwebsite.repositories.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }


    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        return passwordResetTokenRepository.save(passwordResetToken);
    }
}
