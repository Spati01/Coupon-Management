package com.example.coupon.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Cart {

    private List<CartItem> items;

    public BigDecimal calculateCartValue() {
        if (items == null || items.isEmpty()) return BigDecimal.ZERO;

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : items) {
            BigDecimal price = item.getUnitPrice();
            Integer qty = item.getQuantity();

            if (price == null) {
                throw new IllegalArgumentException("unitPrice cannot be null for product: " + item.getProductId());
            }
            if (qty == null) {
                throw new IllegalArgumentException("quantity cannot be null for product: " + item.getProductId());
            }

            total = total.add(price.multiply(BigDecimal.valueOf(qty)));
        }

        return total;
    }

    public int totalItemsCount() {
        if (items == null) return 0;
        return items.stream()
                .map(i -> i.getQuantity() == null ? 0 : i.getQuantity())
                .reduce(0, Integer::sum);
    }

    public Set<String> itemCategories() {
        Set<String> categories = new HashSet<>();
        if (items == null) return categories;

        for (CartItem i : items) {
            if (i.getCategory() != null) {
                categories.add(i.getCategory());
            }
        }

        return categories;
    }
}
