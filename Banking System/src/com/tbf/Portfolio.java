package com.tbf;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Portfolio Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class Portfolio {
	private int portfolioId;
	private String portfolioCode;
	private Person owner;
	private Broker manager;
	private Person beneficiary;
	Map<Asset, Double> assets = new HashMap<>();

	public Portfolio(int portfolioId, String portfolioCode, Person owner, Broker manager, Person beneficiary) {
		this.portfolioId = portfolioId;
		this.portfolioCode = portfolioCode;
		this.owner = owner;
		this.manager = manager;
		this.beneficiary = beneficiary;
	}

	public Portfolio(String portfolioCode, Person owner, Broker manager, Person beneficiary) {
		this.portfolioCode = portfolioCode;
		this.owner = owner;
		this.manager = manager;
		this.beneficiary = beneficiary;
	}

	public int getPortfolioId() {
		return portfolioId;
	}

	public String getPortfolioCode() {
		return portfolioCode;
	}

	public Person getOwner() {
		return owner;
	}

	public Broker getManager() {
		return manager;
	}

	public Person getBeneficiary() {
		return beneficiary;
	}

	public void addAssets(Asset key, double value) {
		this.assets.put(key, value);
	}

	public Map<Asset, Double> getAssets() {
		return assets;
	}

	public String toString() {
		return portfolioCode + "\n" + owner + "\n" + manager + "\n" + beneficiary + "\n" + assets + "\n";
	}
	
	/**
	 * Calulates the total value of a portfolio
	 * @return
	 */
	public double getAggregateTotalValue() {
		double total = 0;
		for (Asset asset : this.assets.keySet()) {
			Double assetValue = this.assets.get(asset);
			total += asset.getTotalValue(assetValue);
		}
		
		return total;
	}

	/**
	 * Calculates the annual return of a portfolio
	 * @return
	 */
	public double getAggregateAnnualReturn() {
		double annualReturn = 0;
		for (Asset asset : this.assets.keySet()) {
			Double assetValue = this.assets.get(asset);
			annualReturn += asset.getAnnualReturn(assetValue);
		}
		
		return annualReturn;
	}
	
	/**
	 * Calculates the aggregate risk of a portfolio
	 * @return
	 */
	public double getAggregateRisk() {
		double aggregateRisk = 0;
		double aggregateTotalValue =getAggregateTotalValue();
		for (Asset asset : this.assets.keySet()) {
			Double assetValue = this.assets.get(asset);
			aggregateRisk += asset.getWeightedRisk() * (asset.getTotalValue(assetValue)/aggregateTotalValue);
		}
		return aggregateRisk;
	}
	
	public static final Comparator<Portfolio> ownernNameCmp = new Comparator<Portfolio>() {
		public int compare(Portfolio a, Portfolio b) {
			if(a.getOwner().getLastName().compareTo(b.getOwner().getLastName()) == 0){
				return a.getOwner().getFirstName().compareTo(b.getOwner().getFirstName());
			} else {
				return a.getOwner().getLastName().compareTo(b.getOwner().getLastName());
			}
		}
	};
	
	public static final Comparator<Portfolio> totalValueCmp = new Comparator<Portfolio>() {
		public int compare(Portfolio a, Portfolio b) {
			Double a1 = a.getAggregateTotalValue();
			Double b1 = b.getAggregateTotalValue();
			return b1.compareTo(a1);
		}
	};
	
	public static final Comparator<Portfolio> managerCmp = new Comparator<Portfolio>() {
		public int compare(Portfolio a, Portfolio b) {
			if(a.manager.getType().compareTo(b.manager.getType()) == 0) {
				if(a.manager.getLastName().compareTo(b.manager.getLastName()) == 0) {
					return a.manager.getFirstName().compareTo(b.manager.getFirstName());
				} else {
					return a.manager.getLastName().compareTo(b.manager.getLastName());
				}
			} else {
				return a.manager.getType().compareTo(b.manager.getType());
			}
		}
	};
	
}
