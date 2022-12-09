package com.example.canifa_shop.Product.Object;

import java.io.Serializable;

public class Product implements Serializable {
    private int ID;
    private String nameProduct;
    private long importPrice;
    private long price;
    private int amount;
    private String type;
    private String describe;
    private String image;
    private String bardCode;

    public Product(int ID, String nameProduct, long importprice, long price, int amount, String type, String describe, String image, String bardCode) {
        this.ID = ID;
        this.nameProduct = nameProduct;
        this.importPrice = importprice;
        this.price = price;
        this.amount = amount;
        this.type = type;
        this.describe = describe;
        this.image = image;
        this.bardCode = bardCode;
    }



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public long getImportprice() {
        return importPrice;
    }

    public void setImportprice(long importprice) {
        this.importPrice = importprice;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBardCode() {
        return bardCode;
    }

    public void setBardCode(String bardCode) {
        this.bardCode = bardCode;
    }
}
