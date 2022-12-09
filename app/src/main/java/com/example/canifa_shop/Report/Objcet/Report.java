package com.example.canifa_shop.Report.Objcet;

public class Report {
    private int ID;
    private String date;
    private long totalImport;
    private long totalSale;
    private long saleMoney;

    public Report(int ID, String date, long totalImport, long totalSale, long saleMoney) {
        this.ID = ID;
        this.date = date;
        this.totalImport = totalImport;
        this.totalSale = totalSale;
        this.saleMoney = saleMoney;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTotalImport() {
        return totalImport;
    }

    public void setTotalImport(long totalImport) {
        this.totalImport = totalImport;
    }

    public long getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(long totalSale) {
        this.totalSale = totalSale;
    }

    public long getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(long saleMoney) {
        this.saleMoney = saleMoney;
    }
}
