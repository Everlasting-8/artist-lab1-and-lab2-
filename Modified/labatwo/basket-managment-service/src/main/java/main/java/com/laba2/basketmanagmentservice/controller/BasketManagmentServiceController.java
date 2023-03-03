package main.java.com.laba2.basketmanagmentservice.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/basket")
public class BasketManagmentServiceController {
    
    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable Long id) {
        // Code to retrieve basket by ID from database
        Basket basket = basketService.getBasketById(id);
        
        if (basket == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(basket);
    }
    
    @PostMapping("/")
    public ResponseEntity<Basket> createBasket(@RequestBody Basket basket) {
        // Code to create new basket in database
        Basket newBasket = basketService.createBasket(basket);
        
        return ResponseEntity.ok(newBasket);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Basket> updateBasket(@PathVariable Long id, @RequestBody Basket basket) {
        // Code to update existing basket in database
        Basket updatedBasket = basketService.updateBasket(id, basket);
        
        if (updatedBasket == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(updatedBasket);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable Long id) {
        // Code to delete basket by ID from database
        boolean deleted = basketService.deleteBasketById(id);
        
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.noContent().build();
    }
}
