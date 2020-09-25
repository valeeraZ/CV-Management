package com.sylvain.chat.system.representation;

import com.sylvain.chat.system.entity.Person;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FriendshipRepresentation {
    private String user_username;
    private String friend_username;
    private int since;
}
