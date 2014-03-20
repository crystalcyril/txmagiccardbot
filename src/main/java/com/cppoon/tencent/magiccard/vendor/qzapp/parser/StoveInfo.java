/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser;

import com.cppoon.tencent.magiccard.api.StoveStatus;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class StoveInfo {

	private String cardThemeName;

	private String cardName;

	private double cardPrice;

	private StoveStatus status;

	private int slotId;

	private long synthesisRemainingTime;
	
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

	/**
	 * @return the slotId
	 */
	public int getSlotId() {
		return slotId;
	}

	/**
	 * @param slotId
	 *            the slotId to set
	 */
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	/**
	 * Returns the number of seconds required to complete the synthesis.
	 * 
	 * @return the synthesisRemainingTime
	 */
	public long getSynthesisRemainingTime() {
		return synthesisRemainingTime;
	}

	/**
	 * Sets the number of seconds required to complete the synthesis.
	 * 
	 * @param synthesisRemainingTime the synthesisRemainingTime to set
	 */
	public void setSynthesisRemainingTime(long synthesisRemainingTime) {
		this.synthesisRemainingTime = synthesisRemainingTime;
	}
	
}
