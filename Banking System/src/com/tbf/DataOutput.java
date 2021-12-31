package com.tbf;

import java.util.List;

/**
 * Data Output Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class DataOutput {
	/**
	 * Outputs a summary report of all the portfolios in the given list
	 * @param portfolioList
	 * 
	 */
	public static void produceSummaryReport(List<Portfolio> portfolios) {	
		
		double feeSum = 0;
		double commissionSum = 0;
		double returnsSum = 0;
		double totalSum = 0;

		System.out.println("Portfolio Summary Report\n"
				+ "============================================================================"
				+ "===============================================");
		System.out.printf("%-10s %-20s %-20s %12s %13s %15s %13s %13s\n", "Portfolio", "Owner", "Manager", "Fees",
				"Commission", "Weighted Risk", "Return", "Total");

		for (int i = 0; i < portfolios.size(); i++) {
			Portfolio portfolio = portfolios.get(i);
			String portfolioCode = portfolio.getPortfolioCode();
			String owner = portfolio.getOwner().getLastName() + ", " + portfolio.getOwner().getFirstName();
			String manager = portfolio.getManager().getLastName() + ", " + portfolio.getManager().getFirstName();

			double returns = portfolio.getAggregateAnnualReturn();
			double total = portfolio.getAggregateTotalValue();
			double risk = portfolio.getAggregateRisk();
			double fee = portfolio.getManager().getFee(portfolio.getAssets().size());
			double commission = portfolio.getManager().getCommission(returns);

			totalSum += total;
			returnsSum += returns;
			feeSum += fee;
			commission = portfolio.getManager().getCommission(returns);
			commissionSum += commission;

			System.out.printf("%-10s %-20s %-20s %12.2f $%12.2f %15.4f $%12.2f $%12.2f\n", portfolioCode, owner,
					manager, fee, commission, risk, returns, total);
		}

		System.out.printf("%124s\n", "-----------------------------------------------------------------------");
		System.out.printf("%54s %10.2f $%12.2f %17s %11.2f $%12.2f\n\n\n\n\n", "Totals $", feeSum, commissionSum, "$",
				returnsSum, totalSum);
		
	}
	
	/**
	 * Produces a detailed report of all the portfolios in the given list
	 * @param portfolios
	 */
	public static void produceDetailedReport(List<Portfolio> portfolios) {
	
		System.out.println("Portfolio Details");
		System.out
				.println("============================================================================================"
						+ "===========================================");

		for (Portfolio portfolio : portfolios) {
			System.out.println("Portfolio " + portfolio.getPortfolioCode());
			System.out.println("----------------------------");
			System.out.println("Owner:");
			System.out.println(portfolio.getOwner());
			System.out.println("Manager:");
			System.out.println(portfolio.getManager().getLastName() + ", " + portfolio.getManager().getFirstName());
			System.out.println("Bineficiary:");
			
			if (portfolio.getBeneficiary() == null) {
				System.out.println("none");
			} else {
				System.out.println(portfolio.getBeneficiary());
			}
			
			System.out.println("Assets:");
		
			double aggregateRisk = portfolio.getAggregateRisk();
			double returns = portfolio.getAggregateAnnualReturn();
			double totalValue = portfolio.getAggregateTotalValue();

			System.out.printf("%-10s %-50s %15s %15s %20s %20s\n", "Code", "Asset", "ReturnRate", "Risk", "Annual Return",
					"Value");

			//calculates return rate, risk, annual return, and value of each assset in the portfolio 
			for (Asset asset : portfolio.assets.keySet()) {
				Double assetValue = portfolio.assets.get(asset);
				double annualRetrunRate = asset.getAnnualReturnRate(assetValue);
				double total = asset.getTotalValue(assetValue);
				double risk = asset.getWeightedRisk();
				double annualReturn = asset.getAnnualReturn(assetValue);
				System.out.printf("%-10s %-50s %14.2f%s %15.2f $%19.2f $%19.2f\n", asset.getCode(),
						asset.getLabel(), annualRetrunRate * 100, "%", risk, annualReturn, total);
			}

			System.out.printf("%135s\n", "---------------------------------------------------------");
			System.out.printf("%77s %15.4f $%19.2f $%19.2f\n\n", "Totals", aggregateRisk, returns, totalValue);
			
		}
	}
	
	/**
	 * Produces a summary report with of all the portfolios in the given sorted list ADT
	 * @param portfolios
	 */
	public static void produceSummaryReportADT(LinkedList<Portfolio> portfolios) {	
		
		double feeSum = 0;
		double commissionSum = 0;
		double returnsSum = 0;
		double totalSum = 0;

		System.out.println("Portfolio Summary Report\n"
				+ "============================================================================"
				+ "===============================================");
		System.out.printf("%-10s %-20s %-20s %12s %13s %15s %13s %13s\n", "Portfolio", "Owner", "Manager", "Fees",
				"Commission", "Weighted Risk", "Return", "Total");

		for (int i = 0; i < portfolios.getSize(); i++) {
			Portfolio portfolio = portfolios.getItem(i);
			String portfolioCode = portfolio.getPortfolioCode();
			String owner = portfolio.getOwner().getLastName() + ", " + portfolio.getOwner().getFirstName();
			String manager = portfolio.getManager().getLastName() + ", " + portfolio.getManager().getFirstName();

			double returns = portfolio.getAggregateAnnualReturn();
			double total = portfolio.getAggregateTotalValue();
			double risk = portfolio.getAggregateRisk();
			double fee = portfolio.getManager().getFee(portfolio.getAssets().size());
			double commission = portfolio.getManager().getCommission(returns);

			totalSum += total;
			returnsSum += returns;
			feeSum += fee;
			commission = portfolio.getManager().getCommission(returns);
			commissionSum += commission;

			System.out.printf("%-10s %-20s %-20s %12.2f $%12.2f %15.4f $%12.2f $%12.2f\n", portfolioCode, owner,
					manager, fee, commission, risk, returns, total);
		}

		System.out.printf("%124s\n", "-----------------------------------------------------------------------");
		System.out.printf("%54s %10.2f $%12.2f %17s %11.2f $%12.2f\n\n\n\n\n", "Totals $", feeSum, commissionSum, "$",
				returnsSum, totalSum);
		
	}
	
}
