package com.github.gabrielvba.ms_order_management.domain.model;

public enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAYMENT_CONFIRMED,
    PICKING,
    SHIPPING,
    IN_ROUTE,
    COMPLETED,
    CANCELED
}