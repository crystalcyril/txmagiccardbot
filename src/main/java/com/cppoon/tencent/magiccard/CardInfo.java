/**
 * 
 */
package com.cppoon.tencent.magiccard;

import java.util.Date;

/**
 * 
 * @author Cyril
 * 
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
