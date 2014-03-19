/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class AccountOverview {

	private int playerLevel;

	private int currentLevelExperience;
	
	private int currentLevelTotalExperience;
	
	private double coins;
	
	private int cardsInDeck;
	
	private int cardsInCardExchangeBox;
	
	private int cardExchangeBoxSize;
	

	/**
	 * Returns the player level.
	 * 
	 * @return
	 */
	public int getPlayerLevel() {
		return playerLevel;
	}

	/**
	 * Sets the player level.
	 * 
	 * @param playerLevel
	 *            the playerLevel to set
	 */
	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
	}

	/**
	 * Returns the current level experience gained by the player.
	 * 
	 * @return the experience
	 */
	public int getCurrentLevelExperience() {
		return currentLevelExperience;
	}

	/**
	 * Sets the current level experience gained by the player.
	 * 
	 * @param experience
	 *            the experience to set
	 */
	public void setCurrentLevelExperience(int currentLevelExperience) {
		this.currentLevelExperience = currentLevelExperience;
	}
	

	/**
	 * @return the nextLevelExperience
	 */
	public int getCurrentLevelTotalExperience() {
		return currentLevelTotalExperience;
	}

	/**
	 * @param currentLevelTotalExperience the nextLevelExperience to set
	 */
	public void setCurrentLevelTotalExperience(int currentLevelTotalExperience) {
		this.currentLevelTotalExperience = currentLevelTotalExperience;
	}

	/**
	 * Returns the number of coins owned by the player.
	 * 
	 * @return the coins
	 */
	public double getCoins() {
		return coins;
	}

	/**
	 * Sets the number of coins owned by the player.
	 * 
	 * @param coins the coins to set
	 */
	public void setCoins(double coins) {
		this.coins = coins;
	}

	/**
	 * Returns the number of cards in the deck.
	 * 
	 * @return the cardsInDeck
	 */
	public int getCardsInDeck() {
		return cardsInDeck;
	}

	/**
	 * Sets the number of cards in the deck.
	 * 
	 * @param cardsInDeck the cardsInDeck to set
	 */
	public void setCardsInDeck(int cardsInDeck) {
		this.cardsInDeck = cardsInDeck;
	}

	/**
	 * Returns the number of cards in the card exchange box.
	 * 
	 * @return the cardsInCardExchangeBox
	 */
	public int getCardsInCardExchangeBox() {
		return cardsInCardExchangeBox;
	}

	/**
	 * Sets the number of cards in the card exchange box.
	 * 
	 * @param cardsInCardExchangeBox the cardsInCardExchangeBox to set
	 */
	public void setCardsInCardExchangeBox(int cardsInCardExchangeBox) {
		this.cardsInCardExchangeBox = cardsInCardExchangeBox;
	}


	/**
	 * Returns the size of the card exchange box.
	 * 
	 * @return the cardExchangeBoxSize
	 */
	public int getCardExchangeBoxSize() {
		return cardExchangeBoxSize;
	}

	/**
	 * Sets the size of the card exchange box.
	 * 
	 * @param cardExchangeBoxSize the cardExchangeBoxSize to set
	 */
	public void setCardExchangeBoxSize(int cardExchangeBoxSize) {
		this.cardExchangeBoxSize = cardExchangeBoxSize;
	}

	public Object getStoveCount() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getFreeStoveCount() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getStealStoveAvailableCount() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getStealStoveSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
