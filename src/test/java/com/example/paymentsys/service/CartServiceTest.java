package com.example.paymentsys.service;

import com.example.paymentsys.dto.CartDto;
import com.example.paymentsys.dto.OrderDto;
import com.example.paymentsys.dto.ProductDto;
import com.example.paymentsys.dto.PromotionDto;
import com.example.paymentsys.enums.PromotionType;
import com.example.paymentsys.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldProcessOrderBuyXGetYFree() {
        String productId = "prod123";
        ProductDto product = new ProductDto();
        product.setId(productId);
        product.setPrice(100);

        PromotionDto promotion = new PromotionDto("promo1", PromotionType.BUY_X_GET_Y_FREE, 2, 1, 0);
        product.setPromotions(Collections.singletonList(promotion));

        when(productService.fetchProductById(productId)).thenReturn(product);

        List<OrderDto> orders = new ArrayList<>();
        orders.add(new OrderDto(productId, 6));

        CartDto cart = cartService.processOrder(orders);

        assertNotNull(cart);
        assertEquals(BigDecimal.valueOf(400), cart.getTotalPrice());
        assertEquals(BigDecimal.valueOf(200), cart.getTotalSavings());
    }

    @Test
    void shouldProcessOrderQtyBasedPriceOverride() {
        String productId = "prod456";
        ProductDto product = new ProductDto();
        product.setId(productId);
        product.setPrice(100);

        PromotionDto promotion = new PromotionDto("promo2", PromotionType.QTY_BASED_PRICE_OVERRIDE, 3, 0, 250);
        product.setPromotions(Collections.singletonList(promotion));

        when(productService.fetchProductById(productId)).thenReturn(product);

        List<OrderDto> orders = new ArrayList<>();
        orders.add(new OrderDto(productId, 7));

        CartDto cart = cartService.processOrder(orders);

        assertNotNull(cart);
        assertEquals(0, cart.getTotalPrice().compareTo(BigDecimal.valueOf(600)));
        assertEquals(0, cart.getTotalSavings().compareTo(BigDecimal.valueOf(100)));
    }

    @Test
    void shouldProcessOrderFlatPercentDiscount() {
        String productId = "prod789";
        ProductDto product = new ProductDto();
        product.setId(productId);
        product.setPrice(200);

        PromotionDto promotion = new PromotionDto("promo3", PromotionType.FLAT_PERCENT, 0, 0, 10);
        product.setPromotions(Collections.singletonList(promotion));

        when(productService.fetchProductById(productId)).thenReturn(product);

        List<OrderDto> orders = new ArrayList<>();
        orders.add(new OrderDto(productId, 5));

        CartDto cart = cartService.processOrder(orders);

        assertNotNull(cart);
        assertEquals(0, cart.getTotalPrice().compareTo(BigDecimal.valueOf(900)));
        assertEquals(0, cart.getTotalSavings().compareTo(BigDecimal.valueOf(100)));
    }

    @Test
    void shouldProcessOrderProductNotFound() {
        String productId = "prod999";

        when(productService.fetchProductById(productId)).thenReturn(null);

        List<OrderDto> orders = new ArrayList<>();
        orders.add(new OrderDto(productId, 1));

        assertThrows(NotFoundException.class, () -> cartService.processOrder(orders));
    }

    @Test
    void shouldProcessOrderEmptyOrder() {
        List<OrderDto> orders = Collections.emptyList();

        CartDto cart = cartService.processOrder(orders);

        assertNotNull(cart);
        assertEquals(BigDecimal.ZERO, cart.getTotalPrice());
        assertEquals(BigDecimal.ZERO, cart.getTotalSavings());
    }

}
