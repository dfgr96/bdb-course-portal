package com.portal.dto;

import lombok.Data;

@Data
public class Login {
    private String token;
    private Long id;
    private String name;

    public Login(String token, Long id, String name) {
        this.id = id;
        this.token = token;
        this.name = name;
    }
}
