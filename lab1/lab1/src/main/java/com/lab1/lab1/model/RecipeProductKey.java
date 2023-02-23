package com.lab1.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
public class RecipeProductKey implements Serializable
{

    @Column
    private int productId;

    @Column
    private int recipeId;

    public RecipeProductKey()
    {
        this.productId = 0;
        this.recipeId = 0;
    }

}
