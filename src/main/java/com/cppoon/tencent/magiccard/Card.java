/**
 * 
 */
package com.cppoon.tencent.magiccard;

import java.util.Date;

/**
 * Defines the API of a card.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface Card {

	/**
	 * The unique ID of this card.
	 * 
	 * @return
	 */
	int getId();

	/**
	 * Returns the parent theme this card belongs to.
	 */
	CardTheme getTheme();

	/**
	 * Name of this card.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Price of this card if sold.
	 * 
	 * @return
	 */
	double getPrice();

	int getType();

	int getPickRate();

	boolean isEnabled();

	/**
	 * Version.
	 * 
	 * @return
	 */
	int getVersion();

	/**
	 * yet to be defined.
	 * 
	 * @return
	 */
	Date getTime();

	/**
	 * Returns the item number. Function unknown yet.
	 * 
	 * @return
	 */
	int getItemNo();

	/**
	 * Returns the composition formula which this card is synthesized. If this
	 * card requires no synthesis (e.g. element cards or special cards which
	 * requires no synthesis), this will return <code>null</code>.
	 * 
	 * @return the composition formula, or <code>null</code> if this card
	 *         requires no synthesis.
	 */
	CardSynthesisFormula getSynthesisFormula();
	
	/**
	 * 
	 * 
	 * @param synthesisTime
	 * @param cards
	 */
	void setSynthesisFormula(long synthesisTime, Card[] cards);

	void setItemNo(int itemNo);

	void setName(String name);

	void setPickRate(int pickRate);

	void setPrice(double price);

	void setTime(Date time);

	void setVersion(int version);

	void setType(int type);

	void setEnabled(boolean enabled);

}
