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
    @NotBlank(message = "{username.blank}")
    @Size(min = 2, max = 32, message = "{username.size}")
    private String username;

    @NotBlank(message = "{name.blank}")
    @Size(min = 2, max = 32, message = "{name.size}")
    private String name;

    @NotBlank(message = "{password.blank}")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "{password.invalid}")
    private String password;

    @NotBlank(message = "{email.invalid}")
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
