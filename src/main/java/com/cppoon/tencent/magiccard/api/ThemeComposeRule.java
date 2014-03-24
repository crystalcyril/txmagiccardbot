package com.cppoon.tencent.magiccard.api;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface ThemeComposeRule {

	/**
	 * @return the themeId
	 */
	int getThemeId();

	/**
	 * @return the state
	 */
	int getState();

	/**
	 * @return the targetCardId
	 */
	int getTargetCardId();

	/**
	 * @return the childrenCardIds
	 */
	int[] getChildrenCardIds();

	/**
	 * @return the buildTime
	 */
	int getBuildTime();

}