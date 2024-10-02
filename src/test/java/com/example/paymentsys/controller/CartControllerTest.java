package com.example.paymentsys.controller;

import com.example.paymentsys.dto.CartDto;
import com.example.paymentsys.dto.OrderDto;
import com.example.paymentsys.exception.NotFoundException;
import com.example.paymentsys.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    /*@Test
    void testAddItemToCart_Success() throws Exception {
        // Prepare the mock behavior
        CartDto mockCart = new CartDto();
        mockCart.setTotalPrice(BigDecimal.valueOf(900)); // Example total price
        mockCart.setTotalSavings(BigDecimal.valueOf(100)); // Example savings

        List<OrderDto> orders = Collections.singletonList(new OrderDto("prod1", 2));

        when(cartService.processOrder(anyList())).thenReturn(mockCart);

        // Perform the request and verify the response
        mockMvc.perform(post("/process-order")
                        .contentType(APPLICATION_JSON)
                        .content("[{\"productId\":\"prod1\", \"qty\":2}]"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPrice").value(900))
                .andExpect(jsonPath("$.totalSavings").value(100));
    }

    @Test
    void testAddItemToCart_NotFound() throws Exception {
        // Prepare the mock behavior to throw NotFoundException
        doThrow(new NotFoundException("Product not found")).when(cartService).processOrder(anyList());

        // Perform the request and verify the response
        mockMvc.perform(post("/process-order")
                        .contentType(APPLICATION_JSON)
                        .content("[{\"productId\":\"prod1\", \"qty\":2}]"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string("null"));
    }

    @Test
    void testAddItemToCart_BadRequest() throws Exception {
        // Prepare the mock behavior to throw IllegalArgumentException
        doThrow(new IllegalArgumentException("Invalid argument")).when(cartService).processOrder(anyList());

        // Perform the request and verify the response
        mockMvc.perform(post("/process-order")
                        .contentType(APPLICATION_JSON)
                        .content("[{\"productId\":\"prod1\", \"qty\":2}]"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string("null"));
    }

    @Test
    void testAddItemToCart_InternalServerError() throws Exception {
        // Prepare the mock behavior to throw a generic Exception
        doThrow(new RuntimeException("Internal error")).when(cartService).processOrder(anyList());

        // Perform the request and verify the response
        mockMvc.perform(post("/process-order")
                        .contentType(APPLICATION_JSON)
                        .content("[{\"productId\":\"prod1\", \"qty\":2}]"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string("null"));
    }*/
}