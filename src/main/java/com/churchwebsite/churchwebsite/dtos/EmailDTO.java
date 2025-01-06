package com.churchwebsite.churchwebsite.dtos;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    @Email(message = "Invalid Email.")
    private String email;
}
