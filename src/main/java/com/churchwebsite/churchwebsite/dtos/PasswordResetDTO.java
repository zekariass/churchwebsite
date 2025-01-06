package com.churchwebsite.churchwebsite.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetDTO {
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotEmpty(message = "You must provide current password")
    private String currentPassword;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotEmpty(message = "You must provide new password")
    private String newPassword;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotEmpty(message = "You must confirm password")
    private String newPasswordConfirm;

    public boolean passwordMatches() {
        if ((newPassword != null && newPasswordConfirm != null)) {
            return newPassword.equals(newPasswordConfirm);
        }

        return false;
    }

}