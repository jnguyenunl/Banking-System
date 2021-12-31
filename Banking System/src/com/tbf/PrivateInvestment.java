package com.tbf;

/**
 * Private Investment Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class PrivateInvestment extends Asset {
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double baseOmegaMeasure;
	private double totalValue;

	public PrivateInvestment(Integer assetId, String code, String type, String label, double quarterlyDividend,
			double baseRateOfReturn, double baseOmegaMeasure, double totalValue) {
		super(assetId, code, type, label);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.baseOmegaMeasure = baseOmegaMeasure;
		this.totalValue = totalValue;
	}

	public PrivateInvestment(String code, String type, String label, double quarterlyDividend, double baseRateOfReturn,
			double baseOmegaMeasure, double totalValue) {
		super(code, type, label);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.baseOmegaMeasure = baseOmegaMeasure;
		this.totalValue = totalValue;
	}

	public double getQuarterlyDividend() {
		return quarterlyDividend;
	}

	public double getBaseRateOfReturn() {
		return baseRateOfReturn;
	}

	public double getBaseOmegaMeasure() {
		return baseOmegaMeasure;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public String toString() {
		return "PrivateInvestment [quarterlyDividend=" + quarterlyDividend + ", baseRateOfReturn=" + baseRateOfReturn
				+ ", baseOmegaMeasure=" + baseOmegaMeasure + ", totalValue=" + totalValue + "]";
	}

	public double getAnnualReturnRate(double assetValue) {
		double returnRate = (getAnnualReturn(assetValue) / assetValue)/ (this.totalValue);

		return returnRate;
	}

	public double getTotalValue(double assetValue) {
		double total = assetValue * this.totalValue;

		return total;
	}

	public double getAnnualReturn(double assetValue) {
		double returns = (((this.baseRateOfReturn) * this.totalValue) + (4 * this.quarterlyDividend)) * assetValue;
		
		return returns;
	}

	public double getWeightedRisk() {
		double o = Math.pow(Math.E, -125500 / this.totalValue);
		double omega = this.baseOmegaMeasure + o;
		double risk = omega;
		return risk;
	}

}
