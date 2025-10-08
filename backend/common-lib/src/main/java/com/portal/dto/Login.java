package com.portal.dto;

import lombok.Data;

@Data
public class Login {
    private String token;
    private Long id;
    private String name;
    private User.Role role;

    public Login(String token, Long id, String name, User.Role role) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.role = role;
    }
}
