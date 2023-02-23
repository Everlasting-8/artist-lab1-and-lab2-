package com.lab1.lab1.controllers;

import lombok.RequiredArgsConstructor;
import com.lab1.lab1.model.User;
import com.lab1.lab1.repositories.UserRepository;
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
public class UserContoller
{
    private final Logger logger = LoggerFactory.getLogger(UserContoller.class);
    private final UserRepository repository;

    @GetMapping("/user/{id}")
    @RolesAllowed({"ROLE_ADMIN"})
    public User get(@PathVariable("id") int userId)
    {
        return repository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Пользователь с id=" + userId + " не найден"));
    }

    @GetMapping("/user")
    @RolesAllowed({"ROLE_ADMIN"})
    public List<User> all()
    {
        return repository.findAll();
    }

    @PostMapping("/user")
    @RolesAllowed({"ROLE_ADMIN"})
    public int save(@RequestBody User user)
    {
        repository.save(user);
        return user.getId();
    }

    @PutMapping("/user")
    @RolesAllowed({"ROLE_ADMIN"})
    public void update(@RequestBody User user)
    {
        logger.info(user.toString());
        if (user.getId() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Поле id не заполнено");
        else
            repository.save(user);
    }

    @DeleteMapping("/user/{id}")
    @RolesAllowed({"ROLE_ADMIN"})
    public void delete(@PathVariable("id") int userId)
    {
        try
        {
            repository.deleteById(userId);
        }
        catch (EmptyResultDataAccessException erda)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с id=" + userId + " не найден");
        }
    }
}
