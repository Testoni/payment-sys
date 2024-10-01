package com.example.paymentsys.service;

import com.example.paymentsys.dto.CartDto;
import com.example.paymentsys.dto.ProductDto;
import com.example.paymentsys.dto.PromotionDto;
import com.example.paymentsys.enums.PromotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    private CartDto cartDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartDto = new CartDto();
    }

    @Test
    void shouldAddItemToCartProductExists() {
        ProductDto product = new ProductDto("PWWe3w1SDU", "Amazing Burger!", 999, Arrays.asList());
        when(productService.fetchProductById("PWWe3w1SDU")).thenReturn(product);

        CartDto updatedCart = cartService.addItemToCart(cartDto, "PWWe3w1SDU");

        assertEquals(1, updatedCart.getItems().size());
        assertEquals(product, updatedCart.getItems().get(0));
    }

    @Test
    void shouldAddItemToCartProductNotFound() {
        when(productService.fetchProductById("invalid-id")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addItemToCart(cartDto, "invalid-id");
        });

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void shouldCalculateTotalPriceNoItems() {
        int totalPrice = cartService.calculateTotalPrice(cartDto);
        assertEquals(0, totalPrice);
        assertEquals(0, cartDto.getTotalSavings());
    }

    @Test
    void shouldCalculateTotalPriceWithPromotion() {
        ProductDto product = new ProductDto("PWWe3w1SDU", "Amazing Burger!", 999,
                Arrays.asList(new PromotionDto("ZRAwbsO2qM", PromotionType.BUY_X_GET_Y_FREE, 2, 1)));

        cartDto.setItems(Arrays.asList(product, product));

        int totalPrice = cartService.calculateTotalPrice(cartDto);
        assertEquals(1998, totalPrice);
        assertEquals(0, cartDto.getTotalSavings());
    }

}
