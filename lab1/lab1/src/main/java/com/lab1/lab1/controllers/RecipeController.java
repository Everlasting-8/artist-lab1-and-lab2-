package com.lab1.lab1.controllers;

import lombok.RequiredArgsConstructor;
import com.lab1.lab1.model.Recipe;
import com.lab1.lab1.repositories.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeController
{
    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    private final RecipeRepository repository;

    @GetMapping("/recipe/{id}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public Recipe get(@PathVariable("id") int recipeId)
    {
        return repository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Рецепт с id=" + recipeId + " не найден"));
    }

    @GetMapping("/recipe")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public List<Recipe> all()
    {
        return repository.findAll();
    }

    @PostMapping("/recipe")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public int save(@RequestBody Recipe recipe)
    {
        repository.save(recipe);
        return recipe.getId();
    }

    @PutMapping("/recipe")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public void update(@RequestBody Recipe recipe)
    {
        logger.info(recipe.toString());
        if (recipe.getId() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Поле id не заполнено");
        else
            repository.save(recipe);
    }

    @DeleteMapping("/recipe/{id}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public void delete(@PathVariable("id") int recipeId)
    {
        try
        {
            repository.deleteById(recipeId);
        }
        catch (EmptyResultDataAccessException erda)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Рецепт с id=" + recipeId + " не найден");
        }
    }
}
