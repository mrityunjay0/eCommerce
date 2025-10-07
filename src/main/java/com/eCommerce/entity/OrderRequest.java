package com.eCommerce.entity;

import lombok.Data;

@Data
public class OrderRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String paymentMethod;
}
