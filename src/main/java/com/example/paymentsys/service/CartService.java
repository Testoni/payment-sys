package com.example.paymentsys.service;

import com.example.paymentsys.dto.CartDto;
import com.example.paymentsys.dto.OrderDto;
import com.example.paymentsys.dto.ProductDto;
import com.example.paymentsys.dto.PromotionDto;
import com.example.paymentsys.enums.PromotionType;
import com.example.paymentsys.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {

    private final ProductService productService;

    public CartDto processOrder(List<OrderDto> orders) {
        Map<String, Integer> productQuantityMap = orders.stream()
                .collect(Collectors.toMap(OrderDto::getProductId, OrderDto::getQty, Integer::sum));

        CartDto cartDto = new CartDto();

        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalSavings = BigDecimal.ZERO;

        for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
            String productId = entry.getKey();
            int orderedQty = entry.getValue();

            ProductDto product = Optional.ofNullable(productService.fetchProductById(productId))
                    .orElseThrow(() -> new NotFoundException(productId));

            BigDecimal productPrice = BigDecimal.valueOf(product.getPrice());
            BigDecimal priceForProduct = BigDecimal.ZERO;
            BigDecimal savingsForProduct = BigDecimal.ZERO;

            if (product.getPromotions() == null || product.getPromotions().size() < 1) {
                totalPrice = totalPrice.add(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(orderedQty)));
                totalSavings = totalSavings.add(BigDecimal.valueOf(0));
            } else {
                for (PromotionDto promotion : product.getPromotions()) {
                    priceForProduct = processPromotion(promotion, orderedQty, productPrice);
                    savingsForProduct = calculateSavings(promotion, orderedQty, productPrice, priceForProduct);

                    totalPrice = totalPrice.add(totalPrice.add(priceForProduct));
                    totalSavings = totalSavings.add(totalSavings.add(savingsForProduct));
                }
            }
        }

        cartDto.setTotalPrice(totalPrice);
        cartDto.setTotalSavings(totalSavings);

        return cartDto;
    }

    private BigDecimal processPromotion(PromotionDto promotion, int orderedQty, BigDecimal productPrice) {
        BigDecimal priceForProduct = BigDecimal.ZERO;

        switch (promotion.getType()) {
            case BUY_X_GET_Y_FREE:
                priceForProduct = calculateBuyXGetYFreePrice(promotion, orderedQty, productPrice);
                break;
            case QTY_BASED_PRICE_OVERRIDE:
                priceForProduct = calculateQtyBasedPriceOverride(promotion, orderedQty, productPrice);
                break;
            case FLAT_PERCENT:
                priceForProduct = calculateFlatPercentPrice(promotion, orderedQty, productPrice);
                break;
            default:
                throw new IllegalArgumentException("Unknown promotion type");
        }

        return priceForProduct;
    }

    private BigDecimal calculateBuyXGetYFreePrice(PromotionDto promotion, int orderedQty, BigDecimal productPrice) {
        int requiredQty = promotion.getRequiredQty();
        int freeQty = promotion.getFreeQty();

        int fullPriceItems = orderedQty / (requiredQty + freeQty) * requiredQty + (orderedQty % (requiredQty + freeQty));
        return productPrice.multiply(BigDecimal.valueOf(fullPriceItems));
    }

    private BigDecimal calculateQtyBasedPriceOverride(PromotionDto promotion, int orderedQty, BigDecimal productPrice) {
        int requiredQtyForOverride = promotion.getRequiredQty();
        BigDecimal overridePrice = BigDecimal.valueOf(promotion.getAmount());

        if (requiredQtyForOverride == 0) {
            requiredQtyForOverride = 1;
        }

        int fullSets = orderedQty / requiredQtyForOverride;
        int remainingQty = orderedQty % requiredQtyForOverride;

        BigDecimal priceForFullSets = overridePrice.multiply(BigDecimal.valueOf(fullSets));
        BigDecimal priceForRemainingQty = productPrice.multiply(BigDecimal.valueOf(remainingQty));

        return priceForFullSets.add(priceForRemainingQty);
    }

    private BigDecimal calculateFlatPercentPrice(PromotionDto promotion, int orderedQty, BigDecimal productPrice) {
        BigDecimal discount = BigDecimal.valueOf(promotion.getAmount());

        BigDecimal totalPriceBeforeDiscount = productPrice.multiply(BigDecimal.valueOf(orderedQty));
        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100)));

        return totalPriceBeforeDiscount.multiply(discountMultiplier);
    }

    private BigDecimal calculateSavings(PromotionDto promotion, int orderedQty, BigDecimal productPrice, BigDecimal priceForProduct) {
        BigDecimal totalPriceWithoutOverride = productPrice.multiply(BigDecimal.valueOf(orderedQty));
        return totalPriceWithoutOverride.subtract(priceForProduct);
    }
}
