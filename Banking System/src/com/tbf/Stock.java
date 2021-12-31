package com.tbf;

/**
 * Stock Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class Stock extends Asset {
	private double quarterlyDividend;
	private double betaMeasure;
	private double baseRateOfReturn;
	private String stockSymbol;
	private double sharePrice;

	public Stock(Integer assetId, String code, String type, String label, double quarterlyDividend,
			double baseRateOfReturn, double betaMeasure, String stockSymbol, double sharePrice) {
		super(assetId, code, type, label);
		this.quarterlyDividend = quarterlyDividend;
		this.betaMeasure = betaMeasure;
		this.baseRateOfReturn = baseRateOfReturn;
		this.stockSymbol = stockSymbol;
		this.sharePrice = sharePrice;

	}

	public Stock(String code, String type, String label, double quarterlyDividend, double baseRateOfReturn,
			double betaMeasure, String stockSymbol, double sharePrice) {
		super(code, type, label);
		this.quarterlyDividend = quarterlyDividend;
		this.betaMeasure = betaMeasure;
		this.baseRateOfReturn = baseRateOfReturn;
		this.stockSymbol = stockSymbol;
		this.sharePrice = sharePrice;

	}

	public double getQuarterlyDividend() {
		return quarterlyDividend;
	}

	public double getBetaMeasure() {
		return betaMeasure;
	}

	public double getBaseRateOfReturn() {
		return baseRateOfReturn;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public double getSharePrice() {
		return sharePrice;
	}

	public String toString(double assetValue) {
		return "Stock [quarterlyDividend=" + quarterlyDividend + ", betaMeasure=" + betaMeasure + ", baseRateOfReturn="
				+ baseRateOfReturn + ", stockSymbol=" + stockSymbol + ", sharePrice=" + sharePrice + "]";
	}

	public double getAnnualReturnRate(double assetValue) {
		double returnRate = getAnnualReturn(assetValue)/ (this.sharePrice * assetValue);

		return returnRate;
	}

	public double getTotalValue(double assetValue) {
		double total = assetValue * this.sharePrice;
		return total;
	}

	public double getAnnualReturn(double assetValue) {
		double returns = ((this.baseRateOfReturn) * this.sharePrice * assetValue) + (4 * this.quarterlyDividend * assetValue);
		
		return returns;
	}

	public double getWeightedRisk() {
		double risk = this.betaMeasure;

		return risk;
	}

}
