/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.io.Serializable;
import java.util.Arrays;

import com.cppoon.tencent.magiccard.api.ThemeComposeRule;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ThemeComposeRuleVo implements ThemeComposeRule, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5701158926810222648L;

	private int themeId;

	private int state;

	private int targetCardId;

	private int[] childrenCardIds;

	private int buildTime;

	/**
	 * 
	 */
	public ThemeComposeRuleVo() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.ThemeComposeRule#getThemeId()
	 */
	public int getThemeId() {
		return themeId;
	}

	/**
	 * @param themeId
	 *            the themeId to set
	 */
	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.impl.ThemeComposeRule#getState()
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.api.impl.ThemeComposeRule#getTargetCardId()
	 */
	public int getTargetCardId() {
		return targetCardId;
	}

	/**
	 * @param targetCardId
	 *            the targetCardId to set
	 */
	public void setTargetCardId(int targetCardId) {
		this.targetCardId = targetCardId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.api.impl.ThemeComposeRule#getChildrenCardIds
	 * ()
	 */
	public int[] getChildrenCardIds() {
		return childrenCardIds;
	}

	/**
	 * @param childrenCardIds
	 *            the childrenCardIds to set
	 */
	public void setChildrenCardIds(int[] childrenCardIds) {
		this.childrenCardIds = childrenCardIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.api.impl.ThemeComposeRule#getBuildTime()
	 */
	public int getBuildTime() {
		return buildTime;
	}

	/**
	 * @param buildTime
	 *            the buildTime to set
	 */
	public void setBuildTime(int buildTime) {
		this.buildTime = buildTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "themeId=" + themeId + ", state=" + state + ", targetCardId="
				+ targetCardId + ", childrenCardIds="
				+ Arrays.toString(childrenCardIds) + ", buildTime=" + buildTime;
	}

}
