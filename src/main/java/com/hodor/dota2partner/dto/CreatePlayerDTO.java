package com.hodor.dota2partner.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreatePlayerDTO implements Serializable {

    @NotNull(message = "Steam Id is mandatory")
    private Long steamId64;
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email is not valid")
    private String email;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password must contain at least 1 uppercase char, 8 characters, 1 digit, and 1 symbol")
    @NotBlank(message = "Password is mandatory")
    private String password;
}
