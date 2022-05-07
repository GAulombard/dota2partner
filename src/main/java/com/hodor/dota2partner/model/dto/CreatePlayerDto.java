package com.hodor.dota2partner.model.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class CreatePlayerDto implements Serializable {

    @NotNull(message = "Steam Id is mandatory")
    private final Long steamId64;
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email is not valid")
    private final String email;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password must contain at least 1 uppercase char, 8 characters, 1 digit, and 1 symbol")
    @NotBlank(message = "Password is mandatory")
    private final String password;
}
