package com.lab2.serviceathurizationandauthenticetion.controller;

import java.util.Iterator;

import org.apache.catalina.Role;
import org.springframework.security.core.Authentication;

public class JwtTokenProvider {

    public String generateToken(Authentication authentication) {
        return null;
    }

    public String createToken(String username, Iterator<Role> roles) {
        return null;
    }

}
