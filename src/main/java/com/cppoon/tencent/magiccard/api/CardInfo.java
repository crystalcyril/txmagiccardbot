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

	int getId();

	int getThemeId();

	String getName();

	int getPrice();

	int getType();

	int getPickRate();

	boolean isEnabled();

	int getVersion();

	Date getTime();

	int getItemNo();

}
