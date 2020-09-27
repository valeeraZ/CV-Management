package com.sylvain.chat.system.representation;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FriendRequestRepresentation {
    private String id;
    private UserPublicRepresentation sender;
    private UserPublicRepresentation recipient;
    private Instant createdAt;
    private boolean isAccepted;
}
