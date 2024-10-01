package com.example.paymentsys.controller;

import com.example.paymentsys.dto.CartDto;
import com.example.paymentsys.dto.CartSummaryDto;
import com.example.paymentsys.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private CartDto cartDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartDto = new CartDto();
    }

    @Test
    void shouldAddItemToCartSuccess() {
        when(cartService.addItemToCart(cartDto, "PWWe3w1SDU")).thenReturn(cartDto);

        ResponseEntity<CartDto> response = cartController.addItemToCart(cartDto, "PWWe3w1SDU");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartDto, response.getBody());
    }

    @Test
    void shouldAddItemToCartProductNotFound() {
        when(cartService.addItemToCart(cartDto, "invalid-id")).thenThrow(new IllegalArgumentException("Product not found"));

        ResponseEntity<CartDto> response = cartController.addItemToCart(cartDto, "invalid-id");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldDisplayCartSummary() {
        when(cartService.calculateTotalPrice(cartDto)).thenReturn(999);
        cartDto.setTotalSavings(999);

        ResponseEntity<CartSummaryDto> response = cartController.displayCartSummary(cartDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(999, response.getBody().getTotalPrice());
        assertEquals(999, response.getBody().getTotalSavings());
    }

}
