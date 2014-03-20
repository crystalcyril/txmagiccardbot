/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser;

import com.cppoon.tencent.magiccard.api.StoveStatus;

/**
 * Information of a stoken stove.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class StolenStove {

	private String cardThemeName;

	private String cardName;

	private double cardPrice;

	private StoveStatus status;

	/**
	 * @return the cardThemeName
	 */
	public String getCardThemeName() {
		return cardThemeName;
	}

	/**
	 * @param cardThemeName
	 *            the cardThemeName to set
	 */
	public void setCardThemeName(String cardThemeName) {
		this.cardThemeName = cardThemeName;
	}

	/**
	 * @return the cardName
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * @param cardName
	 *            the cardName to set
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	/**
	 * @return the cardPrice
	 */
	public double getCardPrice() {
		return cardPrice;
	}

	/**
	 * @param cardPrice
	 *            the cardPrice to set
	 */
	public void setCardPrice(double cardPrice) {
		this.cardPrice = cardPrice;
	}

	/**
	 * @return the status
	 */
	public StoveStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(StoveStatus status) {
		this.status = status;
	}

}
