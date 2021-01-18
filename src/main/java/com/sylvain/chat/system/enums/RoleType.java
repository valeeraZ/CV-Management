package com.sylvain.chat.system.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    USER("USER","user"),
    TEMP_USER("TEMP_USER","temporary user"),
    ADMIN("ADMIN","admin");

    String name;
    String description;


    RoleType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
