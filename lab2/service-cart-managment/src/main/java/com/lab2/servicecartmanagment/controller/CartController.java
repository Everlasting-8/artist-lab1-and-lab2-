package com.lab2.servicecartmanagment.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab2.servicecartmanagment.config.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(@RequestBody CartItemDto cartItemDto, Principal principal) {
        Cart cart = cartService.addItemToCart(cartItemDto, principal.getName());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<Cart> viewCart(Principal principal) {
        Cart cart = cartService.viewCart(principal.getName());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long productId, Principal principal) {
        Cart cart = cartService.removeItemFromCart(productId, principal.getName());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}

