package my.lab1.order.model;

public enum Roles
{

    ADMIN, SUPPLIER, CONSUMER;

    private static final String PREFIX = "ROLE_";

    public String roleName()
    {
        return PREFIX + name();
    }

}
