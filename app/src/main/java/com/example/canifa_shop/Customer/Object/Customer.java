package com.example.canifa_shop.Customer.Object;

import java.io.Serializable;

public class Customer{
    private int IDCustomer;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private String customerPoints;
    private String customerType;
    private String customerVoucher;

    public Customer(int IDCustomer, String customerName, String customerPhone, String customerEmail, String customerAddress, String customerPoints, String customerType, String customerVoucher) {
        this.IDCustomer = IDCustomer;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.customerPoints = customerPoints;
        this.customerType = customerType;
        this.customerVoucher = customerVoucher;
    }

    public int getIDCustomer() {
        return IDCustomer;
    }

    public void setIDCustomer(int IDCustomer) {
        this.IDCustomer = IDCustomer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPoints() {
        return customerPoints;
    }

    public void setCustomerPoints(String customerPoints) {
        this.customerPoints = customerPoints;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerVoucher() {
        return customerVoucher;
    }

    public void setCustomerVoucher(String customerVoucher) {
        this.customerVoucher = customerVoucher;
    }
}
