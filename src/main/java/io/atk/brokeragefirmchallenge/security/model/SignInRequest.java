package io.atk.brokeragefirmchallenge.security.model;

import lombok.Data;

@Data
public class SignInRequest {

    private String username;

    private String password;
}
