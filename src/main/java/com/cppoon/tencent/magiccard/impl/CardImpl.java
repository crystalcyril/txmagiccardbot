/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import java.util.Date;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardSynthesisFormula;
import com.cppoon.tencent.magiccard.CardTheme;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class CardImpl implements Card {
	
	int id;
	
	CardTheme theme;
	
	String name;
	
	int pickRate;
	
	double price;
	
	Date time;
	
	int type;
	
	int version;
	
	int itemNo;
	
	boolean enabled;

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Card#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Card#getTheme()
	 */
	@Override
	public CardTheme getTheme() {
		return theme;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Card#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Card#getPrice()
	 */
	@Override
	public double getPrice() {
		return price;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Card#getType()
	 */
	@Override
	public int getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Card#getPickRate()
	 */
	@Override
	public int getPickRate() {
		return pickRate;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Card#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Card#getVersion()
	 */
	@Override
	public int getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Card#getTime()
	 */
	@Override
	public Date getTime() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Card#getItemNo()
	 */
	@Override
	public int getItemNo() {
		return 0;
	}

	
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme(CardTheme theme) {
		this.theme = theme;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param pickRate the pickRate to set
	 */
	public void setPickRate(int pickRate) {
		this.pickRate = pickRate;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @param itemNo the itemNo to set
	 */
	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Card#getComposition()
	 */
	@Override
	public CardSynthesisFormula getComposition() {
		// TODO Auto-generated method stub
		return null;
	}

}
