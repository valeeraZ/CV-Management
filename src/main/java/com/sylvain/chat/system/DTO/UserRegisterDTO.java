package com.sylvain.chat.system.DTO;

import com.sylvain.chat.system.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @NotBlank
    @Max(value = 32, message = "username.long")
    @Min(value = 2, message = "username.short")
    private String username;

    @NotBlank
    @Max(value = 32, message = "name.long")
    private String name;

    @NotBlank
    @Pattern(regexp = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\\\W]+$)(?![A-Za-z\\\\W]+$)(?![A-Z0-9\\\\W]+$)[a-zA-Z0-9\\\\W]{8,}$",
            message = "password.invalid")
    private String password;

    @Email(message = "email.invalid")
    private String email;

    public User toUser(){
        return User.builder().username(this.getUsername())
                .name(this.getName())
                .password(this.getPassword())
                .email(this.email)
                .enabled(true)
                .build();
    }
}
