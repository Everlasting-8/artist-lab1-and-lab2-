package my.lab1.order.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class RecipeProductKey implements Serializable
{

    @Column
    private int productId;

    @Column
    private int recipeId;

}
