package com.lab1.lab1.model;

public enum Roles
{

    ADMIN, SUPPLIER, CONSUMER;

    private static final String PREFIX = "ROLE_";

    public String roleName()
    {
        return PREFIX + name();
    }

}
