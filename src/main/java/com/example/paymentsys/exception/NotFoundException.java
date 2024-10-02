package com.example.paymentsys.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String productId) {
        super("Product not found with ID: " + productId);
    }
}