package com.sylvain.chat.system.representation;

import com.sylvain.chat.system.entity.Person;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FriendshipRepresentation {
    private UserPublicRepresentation user;
    private UserPublicRepresentation friend;
    private int since;
}
