package com.example.canifa_shop.Manager.Object;

public class Receipt {
    private int IDReceipt;
    private String dateCreate;
    private String IDProduct;
    private int totalProduct;
    private int IDEmployee;

    public Receipt(int IDReceipt, String dateCreate, String IDProduct, int totalProduct, int IDEmployee) {
        this.IDReceipt = IDReceipt;
        this.dateCreate = dateCreate;
        this.IDProduct = IDProduct;
        this.totalProduct = totalProduct;
        this.IDEmployee = IDEmployee;
    }

    public int getIDReceipt() {
        return IDReceipt;
    }

    public void setIDReceipt(int IDReceipt) {
        this.IDReceipt = IDReceipt;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getIDProduct() {
        return IDProduct;
    }

    public void setIDProduct(String IDProduct) {
        this.IDProduct = IDProduct;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public int getIDEmployee() {
        return IDEmployee;
    }

    public void setIDEmployee(int IDEmployee) {
        this.IDEmployee = IDEmployee;
    }
}
