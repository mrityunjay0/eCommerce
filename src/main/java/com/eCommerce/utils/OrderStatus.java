package com.eCommerce.utils;

public enum OrderStatus {

    IN_PROGRESS(1, "In Progress"),
    COMPLETED(2, "Completed"),
    ORDER_RECEIVED(3, "Order Received"),
    PRODUCT_PACKED(4, "Product Packed"),
    SHIPPED(5, "Shipped"),
    OUT_FOR_DELIVERY(6, "Out for Delivery"),
    DELIVERED(7, "Delivered"),
    CANCELLED(8, "Cancelled"),
    SUCCESS(9, "Success");

    private final int id;
    private final String description;

    OrderStatus(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
