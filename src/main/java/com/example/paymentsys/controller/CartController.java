package com.example.paymentsys.controller;

import com.example.paymentsys.dto.CartDto;
import com.example.paymentsys.dto.CartSummaryDto;
import com.example.paymentsys.dto.OrderDto;
import com.example.paymentsys.exception.NotFoundException;
import com.example.paymentsys.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/process-order")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody List<OrderDto> orders) {
        try {
            CartDto updatedCart = cartService.processOrder(orders);
            return ResponseEntity.ok(updatedCart);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    /*@GetMapping("/summary")
    public ResponseEntity<CartSummaryDto> displayCartSummary(@RequestBody CartDto cartDto) {
        int totalPrice = cartService.calculateTotalPrice(cartDto);
        int totalSavings = cartDto.getTotalSavings();
        CartSummaryDto summary = new CartSummaryDto(totalPrice, totalSavings);
        return ResponseEntity.ok(summary);
    }*/
}
