package main.java.com.laba2.ordermanagementservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laba2.ordermanagementservice.controller.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderManagmentServiceController {

  @Autowired
  private OrderService orderService;

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    Order order = orderService.getOrderById(id);
    if (order == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    Order newOrder = orderService.createOrder(order);
    return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
    Order updatedOrder = orderService.updateOrder(id, order);
    if (updatedOrder == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    boolean success = orderService.deleteOrder(id);
    if (success) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}

