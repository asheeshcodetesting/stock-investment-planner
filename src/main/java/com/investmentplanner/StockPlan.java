package com.investmentplanner;

class StockPlan {
    private String stockName;
    private String sector;
    private int monthlyAmount;
    private double dividendYield;
    private int annualDividend;
    private String riskLevel;
    private String portfolioType;

    public StockPlan(String stockName, String sector, int monthlyAmount, double dividendYield, int annualDividend, String riskLevel, String portfolioType) {
        this.stockName = stockName;
        this.sector = sector;
        this.monthlyAmount = monthlyAmount;
        this.dividendYield = dividendYield;
        this.annualDividend = annualDividend;
        this.riskLevel = riskLevel;
        this.portfolioType = portfolioType;
    }

    public String getStockName() { return stockName; }
    public String getSector() { return sector; }
    public int getMonthlyAmount() { return monthlyAmount; }
    public double getDividendYield() { return dividendYield; }
    public int getAnnualDividend() { return annualDividend; }
    public String getRiskLevel() { return riskLevel; }
    public String getPortfolioType() { return portfolioType; }

    public void setMonthlyAmount(int amount) { this.monthlyAmount = amount; }
}
