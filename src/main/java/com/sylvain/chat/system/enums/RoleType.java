package com.sylvain.chat.system.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    USER("USER","utilisateur"),
    TEMP_USER("TEMP_USER","utilisateur temporaire"),
    ADMIN("ADMIN","Gestionaire");

    String name;
    String description;


    RoleType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
