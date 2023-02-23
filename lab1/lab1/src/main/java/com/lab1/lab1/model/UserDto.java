package com.lab1.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto
{

    private String login;

    private String passwordHash;

    private RoleDto role;

    public UserDto() {}

}
