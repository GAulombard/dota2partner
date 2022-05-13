package com.hodor.dota2partner.dto;

import com.hodor.dota2partner.validation.Numeric;
import com.hodor.dota2partner.validation.SteamId64;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreatePlayerDTO implements Serializable {

    @NotNull(message = "Steam Id is mandatory")
    @SteamId64
    //@Numeric
    @Positive(message = "Steam ID must be a positive number")
    @Max(value = 99999999999999999L,message = "Steam ID must contains 17 digits maximum")
    private Long steamId64;
    @NotEmpty(message = "Email is mandatory")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$",
            message = "Email is not valid")
    @Email(message = "Email is not valid")
    private String email;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Safety is number one priority, password must contain at least 1 uppercase char, 1 digit, 1 symbol, and 8 characters minimum")
    @NotBlank(message = "Password is mandatory")
    private String password;
}
