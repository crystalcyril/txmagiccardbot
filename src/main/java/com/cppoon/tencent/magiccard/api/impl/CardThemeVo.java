/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.util.Date;

import com.cppoon.tencent.magiccard.api.CardTheme;

/**
 * 
 * 
 * @author Cyril
 */
public class CardThemeVo implements CardTheme {

	int id;

	String name;

	int difficulty;

	Date publishTime;

	int pickRate;

	boolean enabled;

	double coins;

	int experience;

	int[] cardIds;

	int type;

	int version;

	Date time;

	Date expiryTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getId()
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getName()
	 */

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getDifficulty()
	 */

	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty
	 *            the difficulty to set
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getPublishTime()
	 */

	public Date getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime
	 *            the publishTime to set
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getPickRate()
	 */

	public int getPickRate() {
		return pickRate;
	}

	/**
	 * @param pickRate
	 *            the pickRate to set
	 */
	public void setPickRate(int pickRate) {
		this.pickRate = pickRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#isEnabled()
	 */

	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getPrize()
	 */

	public double getCoins() {
		return coins;
	}

	/**
	 * 
	 * 
	 * @param coins
	 *            the prize to set
	 */
	public void setCoins(double coins) {
		this.coins = coins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getScore()
	 */

	public int getExperience() {
		return experience;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		this.experience = score;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getCardIds()
	 */

	public int[] getCardIds() {
		return cardIds;
	}

	/**
	 * @param cardIds
	 *            the cardIds to set
	 */
	public void setCardIds(int[] cardIds) {
		this.cardIds = cardIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getThemeType()
	 */

	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getVersion()
	 */

	public int getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getTime()
	 */

	public Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.CardTheme#getExpiryTime()
	 */

	public Date getExpiryTime() {
		return expiryTime;
	}

	/**
	 * @param expiryTime
	 *            the expiryTime to set
	 */
	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

}
