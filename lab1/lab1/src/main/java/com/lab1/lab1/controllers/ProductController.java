package com.lab1.lab1.controllers;

import lombok.RequiredArgsConstructor;
import com.lab1.lab1.model.Product;
import com.lab1.lab1.repositories.ProductRepository;
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
public class ProductController
{

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepository repository;

    @GetMapping("/product/{id}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public Product get(@PathVariable("id") int productId)
    {
        return repository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Продукт с id=" + productId + " не найден"));
    }

    @GetMapping("/product")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public List<Product> all()
    {
        return repository.findAll();
    }

    @PostMapping("/product")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public int save(@RequestBody Product product)
    {
        repository.save(product);
        return product.getId();
    }

    @PutMapping("/product")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public void update(@RequestBody Product product)
    {
        logger.info(product.toString());
        if (product.getId() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Поле id не заполнено");
        else
            repository.save(product);
    }

    @DeleteMapping("/product/{id}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public void delete(@PathVariable("id") int productId)
    {
        try
        {
            repository.deleteById(productId);
        }
        catch (EmptyResultDataAccessException erda)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Продукт с id=" + productId + " не найден");
        }
    }

}
