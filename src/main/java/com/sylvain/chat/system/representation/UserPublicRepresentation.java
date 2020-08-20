package com.sylvain.chat.system.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The information which is public to everyone: username, name and enabled/disabled status
 * On the contrary, the email, birthday... might be private in consideration of user's setting
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPublicRepresentation {
    private String username;
    private String name;
    private boolean enabled;
}
