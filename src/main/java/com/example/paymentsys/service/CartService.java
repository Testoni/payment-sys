package com.example.paymentsys.service;

import com.example.paymentsys.dto.CartDto;
import com.example.paymentsys.dto.ProductDto;
import com.example.paymentsys.dto.PromotionDto;
import com.example.paymentsys.enums.PromotionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final ProductService productService;

    public CartDto addItemToCart(CartDto cartDto, String productId) {
        List<ProductDto> items = cartDto.getItems();
        if (items == null) {
            items = new ArrayList<>();
        }

        ProductDto product = productService.fetchProductById(productId);
        if (product != null) {
            items.add(product);
            cartDto.setItems(items);
        } else {
            throw new IllegalArgumentException("Product not found");
        }

        return cartDto;
    }

    public int calculateTotalPrice(CartDto cartDto) {
        List<ProductDto> items = cartDto.getItems();
        int totalPrice = 0;
        int totalSavings = 0;

        if (items != null) {
            for (ProductDto item : items) {
                int itemPrice = item.getPrice();
                PromotionDto appliedPromotion = applyPromotion(item);
                if (appliedPromotion != null) {
                    totalSavings += calculateSavings(item, appliedPromotion);
                }
                totalPrice += itemPrice;
            }
        }

        cartDto.setTotalSavings(totalSavings);
        return totalPrice - totalSavings;
    }

    public int calculateSavings(ProductDto product, PromotionDto promotion) {
        int savings = 0;
        if (promotion.getType() == PromotionType.BUY_X_GET_Y_FREE) {
            int requiredQty = promotion.getRequiredQty();
            int freeQty = promotion.getFreeQty();
            int productCount = getProductCount(product.getId(), product);

            if (productCount >= requiredQty) {
                savings = (productCount / requiredQty) * freeQty * product.getPrice();
            }
        }
        return savings;
    }

    private int getProductCount(String productId, ProductDto product) {
        return 1;//(int) product.getItems().stream().filter(p -> p.getId().equals(productId)).count();
    }

    private PromotionDto applyPromotion(ProductDto product) {
        if (!product.getPromotions().isEmpty()) {
            return product.getPromotions().get(0);
        }
        return null;
    }

}
