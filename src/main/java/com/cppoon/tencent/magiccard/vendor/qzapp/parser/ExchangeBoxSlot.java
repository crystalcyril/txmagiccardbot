/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser;

import java.io.Serializable;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ExchangeBoxSlot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4931474991014442519L;

	private String cardThemeName;
	
	private String cardName;
	
	private double cardPrice;
	
	private int cardId;
	
	private int slotId;

	/**
	 * @return the cardThemeName
	 */
	public String getCardThemeName() {
		return cardThemeName;
	}

	/**
	 * @param cardThemeName the cardThemeName to set
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
	 * @param cardName the cardName to set
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
	 * @param cardPrice the cardPrice to set
	 */
	public void setCardPrice(double cardPrice) {
		this.cardPrice = cardPrice;
	}

	/**
	 * @return the cardId
	 */
	public int getCardId() {
		return cardId;
	}

	/**
	 * @param cardId the cardId to set
	 */
	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the slotId
	 */
	public int getSlotId() {
		return slotId;
	}

	/**
	 * @param slotId the slotId to set
	 */
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "cardThemeName=" + cardThemeName + ", cardName=" + cardName
				+ ", cardPrice=" + cardPrice + ", cardId=" + cardId
				+ ", slotId=" + slotId;
	}
	
}
