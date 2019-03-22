package com.ecommerceapp.exceptions;

import com.ecommerceapp.entity.InventoryEntity;

public class InsufficientQuantityException extends Exception {
    public InsufficientQuantityException(InventoryEntity product) {
        super("Insufficient quantity for product " + product.getName());
    }
}
