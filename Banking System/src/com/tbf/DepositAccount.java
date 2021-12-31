package com.tbf;

/**
 * Deposit Account Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class DepositAccount extends Asset {
	private double apr;

	public DepositAccount(Integer assetId, String code, String type, String label, double apr) {
		super(assetId, code, type, label);
		this.apr = apr;
	}

	public DepositAccount(String code, String type, String label, double apr) {
		super(code, type, label);
		this.apr = apr;
	}

	public double getApr() {
		return apr;
	}

	public String toString() {
		return "DepositAccount [apr=" + apr + "]";
	}

	public double getAnnualReturnRate(double assetValue) {
		double returnRate = (Math.pow(Math.E, this.getApr()) - 1);

		return returnRate;
	}

	public double getTotalValue(double assetValue) {
		double total = assetValue;

		return total;
	}

	public double getAnnualReturn(double assetValue) {
		double returns = (Math.pow(Math.E, this.apr) - 1) * assetValue;

		return returns;
	}

	public double getWeightedRisk() {
		double risk = 0;

		return risk;
	}

}
