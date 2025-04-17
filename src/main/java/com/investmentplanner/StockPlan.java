package com.investmentplanner;

public class StockPlan {
    private String stockName;
    private String sector;
    private int monthlyAmount;
    private double dividendYield;
    private int annualDividend;

    public StockPlan(String stockName, String sector, int monthlyAmount, double dividendYield, int annualDividend) {
        this.stockName = stockName;
        this.sector = sector;
        this.monthlyAmount = monthlyAmount;
        this.dividendYield = dividendYield;
        this.annualDividend = annualDividend;
    }

    public String getStockName() { return stockName; }
    public String getSector() { return sector; }
    public int getMonthlyAmount() { return monthlyAmount; }
    public double getDividendYield() { return dividendYield; }
    public int getAnnualDividend() { return annualDividend; }
}