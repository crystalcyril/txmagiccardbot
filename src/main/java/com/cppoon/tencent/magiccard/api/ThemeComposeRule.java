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
	public abstract int getThemeId();

	/**
	 * @return the state
	 */
	public abstract int getState();

	/**
	 * @return the targetCardId
	 */
	public abstract int getTargetCardId();

	/**
	 * @return the childrenCardIds
	 */
	public abstract int[] getChildrenCardIds();

	/**
	 * @return the buildTime
	 */
	public abstract int getBuildTime();

}