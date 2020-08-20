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
    @Size(min = 2, max = 32, message = "{username.size}")
    private String username;

    @NotBlank
    @Size(min = 2, max = 32, message = "{name.size}")
    private String name;

    @NotBlank
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "{password.invalid}")
    private String password;

    @Email(message = "{email.invalid}")
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
