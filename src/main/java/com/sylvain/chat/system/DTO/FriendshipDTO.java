package com.sylvain.chat.system.DTO;

import com.sylvain.chat.system.entity.Friendship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDTO {
    private String user_username;
    private String friend_username;


}
