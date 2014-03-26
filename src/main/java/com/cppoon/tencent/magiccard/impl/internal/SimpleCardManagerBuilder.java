/**
 * 
 */
package com.cppoon.tencent.magiccard.impl.internal;

import java.util.Date;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardManager.Builder;
import com.cppoon.tencent.magiccard.CardTheme;
import com.cppoon.tencent.magiccard.impl.CardImpl;


/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SimpleCardManagerBuilder implements CardManager.Builder {
	
	private int id;
	
	private boolean enabled;
	
	private int itemNumber;
	
	private CardTheme theme;
	
	private String name;
	
	private int pickRate;
	
	private double price;
	
	private Date time;
	
	private int type;
	
	private int version;
	

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager.Builder#id(int)
	 */
	@Override
	public Builder id(int id) {
		this.id = id;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager.Builder#itemNumber(int)
	 */
	@Override
	public Builder itemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager.Builder#enabled(boolean)
	 */
	@Override
	public Builder enabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager.Builder#theme(com.cppoon.tencent.magiccard.CardTheme)
	 */
	@Override
	public Builder theme(CardTheme theme) {
		this.theme = theme;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager.Builder#name(java.lang.String)
	 */
	@Override
	public Builder name(String name) {
		this.name = name;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager.Builder#pickRate(int)
	 */
	@Override
	public Builder pickRate(int pickRate) {
		this.pickRate = pickRate;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager.Builder#price(double)
	 */
	@Override
	public Builder price(double price) {
		this.price = price;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager.Builder#time(java.util.Date)
	 */
	@Override
	public Builder time(Date when) {
		this.time = when;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager.Builder#type(int)
	 */
	@Override
	public Builder type(int type) {
		this.type = type;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager.Builder#version(int)
	 */
	@Override
	public Builder version(int version) {
		this.version = version;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager.Builder#build()
	 */
	@Override
	public Card build() {
		
		CardImpl ret = new CardImpl();
		
		ret.setEnabled(this.enabled);
		ret.setId(this.id);
		ret.setItemNo(this.itemNumber);
		ret.setName(this.name);
		ret.setPickRate(this.pickRate);
		ret.setPrice(this.price);
		ret.setTheme(theme);
		ret.setTime(this.time);
		ret.setType(this.type);
		ret.setVersion(this.version);
		
		return ret;
	}

	
}
