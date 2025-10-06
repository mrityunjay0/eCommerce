package com.eCommerce.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderId;

    private Date orderDate;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    private Double price;

    private Integer quantity;

    private String status;

    private String paymentMethod;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderAddress orderAddress;


    public ProductOrder() {
    }

    public ProductOrder(Integer id, String orderId, Date orderDate, Product product,
                        User user, Double price, Integer quantity, String status,
                        String paymentMethod, OrderAddress orderAddress) {
        this.id = id;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.product = product;
        this.user = user;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.orderAddress = orderAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }
}