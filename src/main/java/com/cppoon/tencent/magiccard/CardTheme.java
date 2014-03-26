/**
 * 
 */
package com.cppoon.tencent.magiccard;

import java.util.Date;
import java.util.List;

/**
 * Defines the API of a card theme.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface CardTheme {

	/**
	 * The unique ID which identifies this theme.
	 * <p>
	 * 
	 * This method will not return <code>null</code>.
	 * 
	 * @return the id
	 */
	int getId();

	/**
	 * Returns the textual name of this theme.
	 * <p>
	 * 
	 * This method will not return <code>null</code>.
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * The difficulties, or star/level of this theme.
	 * <p>
	 * 
	 * Normal range is from 1 to 5.
	 * 
	 * @return the difficulty
	 */
	int getDifficulty();

	/**
	 * The publish date and time.
	 * <p>
	 * 
	 * If the publish date and time is not known, this methdo should return
	 * <code>null</code>.
	 * 
	 * @return the publishTime
	 */
	Date getPublishTime();

	/**
	 * Unknown.
	 * 
	 * @return the pickRate
	 */
	int getPickRate();

	/**
	 * Unknown.
	 * 
	 * @return the enabled
	 */
	boolean isEnabled();

	/**
	 * Returns the coins rewarded if the theme is completely synthesized.
	 * 
	 * @return the prize
	 */
	double getCoins();

	/**
	 * Returns the experienced rewarded if the is completed synthesized.
	 * 
	 * @return the score
	 */
	int getExperience();

	/**
	 * 
	 * 
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

	/**
	 * Returns the top level cards this theme is made of.
	 * 
	 * @return
	 */
	List<Card> getChildrenCards();

	/**
	 * Find card in this theme.
	 * 
	 * @param cardId
	 * @return
	 */
	Card getCardById(int cardId);

	/**
	 * The text associated with this theme.
	 * 
	 * @return
	 */
	String getText();

	void setDifficulty(int difficulty);

	void setExperience(int experience);

	void setExpiryTime(Date expiryTime);

	void setName(String name);

	void setCoins(double coins);

	void setPickRate(int pickRate);

	void setPublishTime(Date publishTime);

	void setText(String text);

	void setTime(Date time);

	void setType(int type);

	void setVersion(int version);

	void setEnabled(boolean enabled);

}
