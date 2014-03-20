/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp;

import java.util.List;

import com.cppoon.tencent.magiccard.api.StoveStatus;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StolenStove;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StoveInfo;

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
	
	List<StoveInfo> stoves;
	
	List<StolenStove> stolenStoves;
	

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

	public int getStoveCount() {
		return (stoves == null) ? 0 : stoves.size();
	}
	
	public void setStoveInfos(List<StoveInfo> stoves) {
		this.stoves = stoves;
	}
	

	public int getFreeStoveCount() {
		if (stoves == null) return 0;
		
		int count = 0;
		for (StoveInfo stove : stoves) {
			if (stove.getStatus() == StoveStatus.IDLE) count++;
		}
		
		return count;
	}

	public int getStealStoveAvailableCount() {
		if (stolenStoves == null) return 0;
		
		int count = 0;
		for (StolenStove s : stolenStoves) {
			if (s.getStatus() == StoveStatus.IDLE) {
				count++;
			}
		}
		
		return count;

	}

	public int getStealStoveSize() {
		return stolenStoves == null ? 0 : stolenStoves.size();
	}

	public void setStolenStoveInfos(List<StolenStove> stolenStoves) {
		this.stolenStoves = stolenStoves;
	}

}
