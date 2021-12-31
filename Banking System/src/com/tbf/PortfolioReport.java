package com.tbf;

import java.util.List;

/**
 * Portfolio Report Classs
 * @author James Nguyen and Thi Hoang
 *
 */
public class PortfolioReport {

	public static void main(String args[]) {
		
		List<Portfolio> portfolios = DatabaseReading.getAllPortfolios();
		
		LinkedList<Portfolio> ownerNameSort = new LinkedList<>(Portfolio.ownernNameCmp);
		for (int i = 0; i < portfolios.size(); i++) {
			ownerNameSort.insertItem(portfolios.get(i));
		}
		DataOutput.produceSummaryReportADT(ownerNameSort);
		
		LinkedList<Portfolio> totalValueSort = new LinkedList<>(Portfolio.totalValueCmp);
		for (int i = 0; i < portfolios.size(); i++) {
			totalValueSort.insertItem(portfolios.get(i));
		}
		DataOutput.produceSummaryReportADT(totalValueSort);

		LinkedList<Portfolio> managerNameSort = new LinkedList<>(Portfolio.managerCmp);
		for (int i = 0; i < portfolios.size(); i++) {
			managerNameSort.insertItem(portfolios.get(i));
		}
		DataOutput.produceSummaryReportADT(managerNameSort);
	
	}
}
