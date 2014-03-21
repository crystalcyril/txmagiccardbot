/**
 * 
 */
package com.cppoon.tencent.magiccard.api;

import java.util.Date;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface CardInfo {

	/**
	 * The unique ID of this card.
	 * 
	 * @return
	 */
	int getId();

	/**
	 * The theme which this card belongs to.
	 * 
	 * @return
	 */
	int getThemeId();

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
	 * Unknown
	 * 
	 * @return
	 */
	int getItemNo();

}
