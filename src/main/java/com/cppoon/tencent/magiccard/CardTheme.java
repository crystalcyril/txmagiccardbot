package com.cppoon.tencent.magiccard;

import java.util.Date;

public interface CardTheme {

	/**
	 * @return the id
	 */
	int getId();

	/**
	 * @return the name
	 */
	String getName();

	/**
	 * @return the difficulty
	 */
	int getDifficulty();

	/**
	 * @return the publishTime
	 */
	Date getPublishTime();

	/**
	 * @return the pickRate
	 */
	int getPickRate();

	/**
	 * @return the enabled
	 */
	boolean isEnabled();

	/**
	 * @return the prize
	 */
	int getPrize();

	/**
	 * @return the score
	 */
	int getScore();

	/**
	 * @return the cardIds
	 */
	int[] getCardIds();

	/**
	 * @return the themeType
	 */
	int getType();

	/**
	 * @return the version
	 */
	int getVersion();

	/**
	 * @return the time
	 */
	Date getTime();

	/**
	 * @return the expiryTime
	 */
	Date getExpiryTime();

}