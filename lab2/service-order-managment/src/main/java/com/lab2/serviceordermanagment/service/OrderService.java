package com.lab2.serviceordermanagment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab2.serviceordermanagment.controller.Order;
import com.lab2.serviceordermanagment.controller.OrderRequest;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartClient cartClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
    }

    public Order createOrder(OrderRequest orderRequest) {
        // Call Cart Management Service to get cart by user id
        Cart cart = cartClient.getCartByUserId(orderRequest.getUserId());

        // Create new order
        Order order = new Order();
        order.setUserId(orderRequest.getUserId());
        order.setTotal(cart.getTotal());
        order.setItems(cart.getItems());

        // Save order
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
    }

    public Order updateOrder(Long orderId, OrderRequest orderRequest) {
        return null;
    }
}
