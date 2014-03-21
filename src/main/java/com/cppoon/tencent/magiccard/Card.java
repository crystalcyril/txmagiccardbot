/**
 * 
 */
package com.cppoon.tencent.magiccard;

import java.util.Date;

/**
 * API to the card.
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
	int getPrice();

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
	CardComposition getComposition();

}
