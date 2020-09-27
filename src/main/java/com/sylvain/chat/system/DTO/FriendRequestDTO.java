package com.sylvain.chat.system.DTO;

import com.sylvain.chat.system.entity.Friendship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDTO {
    @NotBlank
    private String recipient_username;
}
