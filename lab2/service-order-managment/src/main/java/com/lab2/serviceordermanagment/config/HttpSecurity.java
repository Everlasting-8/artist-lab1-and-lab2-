package com.lab2.serviceordermanagment.config;

public class HttpSecurity {

    public Object csrf() {
        return null;
    }

    public void addFilterBefore(JwtRequestFilter jwtRequestFilter, Class<UsernamePasswordAuthenticationFilter> class1) {
    }

}
