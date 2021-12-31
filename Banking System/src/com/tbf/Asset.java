package com.tbf;

/**
 * Asset Class
 * @author James Nguyen and Thi Hoang
 *
 */
public abstract class Asset {
	private int assetId;
	private String code;
	private String type;
	private String label;

	public Asset(int assetId, String code, String type, String label) {
		this.assetId = assetId;
		this.code = code;
		this.type = type;
		this.label = label;
	}

	public Asset(String code, String type, String label) {
		this.code = code;
		this.type = type;
		this.label = label;
	}

	public Integer getAssetId() {
		return assetId;
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public String getLabel() {
		return label;
	}

	public String toString() {
		return "Asset [code=" + code + ", type=" + type + ", label=" + label + "]";
	}

	/**
	 * Calculates the annual return rate of a specific asset given the value of the asset
	 * @return
	 */
	public abstract double getAnnualReturnRate(double assetValue);
	
	/**
	 * Calculates the total value of a specific asset given the value of the asset
	 * @param assetValue
	 * @return
	 */
	public abstract double getTotalValue(double assetValue);
	
	/**
	 * Calculates the annual return of a specific asset given the value of the asset
	 * @param assetValue
	 * @return
	 */
	public abstract double getAnnualReturn(double assetValue);
	
	/**
	 * Calculates the  weighted risk of a specific asset 
	 * @param assetValue
	 * @param total
	 * @return
	 */
	public abstract double getWeightedRisk();

}
