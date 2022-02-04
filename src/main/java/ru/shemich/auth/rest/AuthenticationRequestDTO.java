package ru.shemich.auth.rest;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String login;
    private String password;
}
