package com.lab1.lab1.controllers;

import lombok.RequiredArgsConstructor;
import com.lab1.lab1.model.Order;
import com.lab1.lab1.repositories.OrderRepository;
import com.lab1.lab1.service.OrderMakingService;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController
{

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderRepository repository;
    private final OrderMakingService orderMakingService;

    @GetMapping("/order/{id}")
    public Order get(@PathVariable("id") int orderId)
    {
        return repository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Заказ с id=" + orderId + " не найден"));
    }

    @GetMapping("/order")
    public List<Order> all()
    {
        return repository.findAll();
    }

    @PostMapping("/order/make")
    public void make(@RequestBody Map<String, Integer> positionIdToCount)
    {
        if (!orderMakingService.make(positionIdToCount))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Недостаточно товара на складе");
    }

    @PostMapping("/order")
    @RolesAllowed({"ROLE_ADMIN"})
    public int save(@RequestBody Order order)
    {
        repository.save(order);
        return order.getId();
    }

    @PutMapping("/order")
    @RolesAllowed({"ROLE_ADMIN"})
    public void update(@RequestBody Order order)
    {
        logger.info(order.toString());
        if (order.getId() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Поле id не заполнено");
        else
            repository.save(order);
    }

    @DeleteMapping("/order/{id}")
    @RolesAllowed({"ROLE_ADMIN"})
    public void delete(@PathVariable("id") int orderId)
    {
        try
        {
            repository.deleteById(orderId);
        }
        catch (EmptyResultDataAccessException erda)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Заказ с id=" + orderId + " не найден");
        }
    }

}
