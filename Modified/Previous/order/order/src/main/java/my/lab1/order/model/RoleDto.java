package my.lab1.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleDto
{

    private int id;

    private String name;

    public RoleDto() {}

}
