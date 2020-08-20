package com.sylvain.chat.system.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * The information which is private only to user himself
 * like email, birthday...
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrivateRepresentation {
    private String username;
    private String name;
    private String email;
    private Instant createdAt;
    private boolean enabled;
}
