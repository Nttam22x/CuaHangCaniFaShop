package com.example.canifa_shop.Bill.Object;
public class Bill {
    private int IDBill;
    private String date;
    private String names;
    private String amount;
    private String price;
    private long total;
    private int IDCustomer;
    private int IDEmployee;

    public Bill(int IDBill, String date, String names, String amount, String price, long total, int IDCustomer, int IDEmployee) {
        this.IDBill = IDBill;
        this.date = date;
        this.names = names;
        this.amount = amount;
        this.price = price;
        this.total = total;
        this.IDCustomer = IDCustomer;
        this.IDEmployee = IDEmployee;
    }

    public int getIDBill() {
        return IDBill;
    }

    public void setIDBill(int IDBill) {
        this.IDBill = IDBill;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getIDCustomer() {
        return IDCustomer;
    }

    public void setIDCustomer(int IDCustomer) {
        this.IDCustomer = IDCustomer;
    }

    public int getIDEmployee() {
        return IDEmployee;
    }

    public void setIDEmployee(int IDEmployee) {
        this.IDEmployee = IDEmployee;
    }
}
