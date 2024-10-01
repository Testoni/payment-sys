package com.example.paymentsys.controller;

import com.example.paymentsys.dto.CartDto;
import com.example.paymentsys.dto.CartSummaryDto;
import com.example.paymentsys.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody CartDto cartDto, @RequestParam String productId) {
        try {
            CartDto updatedCart = cartService.addItemToCart(cartDto, productId);
            return ResponseEntity.ok(updatedCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<CartSummaryDto> displayCartSummary(@RequestBody CartDto cartDto) {
        int totalPrice = cartService.calculateTotalPrice(cartDto);
        int totalSavings = cartDto.getTotalSavings();
        CartSummaryDto summary = new CartSummaryDto(totalPrice, totalSavings);
        return ResponseEntity.ok(summary);
    }
}
