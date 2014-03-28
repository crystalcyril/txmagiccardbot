/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SynthesizeCardInfo {

	private int cardId;
	
	private String cardName;
	
	private int cardThemeId;
	
	private double cardPrice;
	
	private int cardsCollected;
	
	private String synthesizeUrl;

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
	 * @return the cardThemeId
	 */
	public int getCardThemeId() {
		return cardThemeId;
	}

	/**
	 * @param cardThemeId the cardThemeId to set
	 */
	public void setCardThemeId(int cardThemeId) {
		this.cardThemeId = cardThemeId;
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
	 * @return the cardsCollected
	 */
	public int getCardsCollected() {
		return cardsCollected;
	}

	/**
	 * @param cardsCollected the cardsCollected to set
	 */
	public void setCardsCollected(int cardsCollected) {
		this.cardsCollected = cardsCollected;
	}

	/**
	 * @return the synthesizeUrl
	 */
	public String getSynthesizeUrl() {
		return synthesizeUrl;
	}

	/**
	 * @param synthesizeUrl the synthesizeUrl to set
	 */
	public void setSynthesizeUrl(String synthesizeUrl) {
		this.synthesizeUrl = synthesizeUrl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "cardId=" + cardId + ", cardName=" + cardName + ", cardThemeId="
				+ cardThemeId + ", cardPrice=" + cardPrice
				+ ", cardsCollected=" + cardsCollected + ", synthesizeUrl="
				+ synthesizeUrl;
	}
	
}
