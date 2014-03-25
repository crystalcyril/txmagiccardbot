/**
 * 
 */
package com.cppoon.tencent.magiccard.impl.internal;

import java.util.Date;

import com.cppoon.tencent.magiccard.CardTheme;
import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.CardThemeManager.Builder;
import com.cppoon.tencent.magiccard.impl.CardThemeImpl;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SimpleCardThemeManagerBuilderImpl implements
		CardThemeManager.Builder {

	private int id;

	private String name;

	private int difficulty;

	private double coins;
	
	private int experience;

	private Date publishTime;

	private int type;

	private int color;

	private Date time;

	private Date expiryTime;

	private int version;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardThemeManager.Builder#id(int)
	 */
	@Override
	public Builder id(int id) {
		this.id = id;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.CardThemeManager.Builder#name(java.lang.
	 * String)
	 */
	@Override
	public Builder name(String name) {
		this.name = name;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.CardThemeManager.Builder#difficulty(int)
	 */
	@Override
	public Builder difficulty(int difficulty) {
		this.difficulty = difficulty;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardThemeManager.Builder#prize(double)
	 */
	@Override
	public Builder coins(double prize) {
		this.coins = prize;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.CardThemeManager.Builder#publishTime(java
	 * .util.Date)
	 */
	@Override
	public Builder publishTime(Date when) {
		this.publishTime = when;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardThemeManager.Builder#type(int)
	 */
	@Override
	public Builder type(int type) {
		this.type = type;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardThemeManager.Builder#version(int)
	 */
	@Override
	public Builder version(int version) {
		this.version = version;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardThemeManager.Builder#color(int)
	 */
	@Override
	public Builder color(int color) {
		this.color = color;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.CardThemeManager.Builder#time(java.util.
	 * Date)
	 */
	@Override
	public Builder time(Date when) {
		this.time = when;
		return this;
	}

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.CardThemeManager.Builder#expiryTime(java
	 * .util.Date)
	 */
	@Override
	public Builder expiryTime(Date when) {
		this.expiryTime = when;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardThemeManager.Builder#experience(int)
	 */
	@Override
	public Builder experience(int experience) {
		this.experience = experience;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardThemeManager.Builder#build()
	 */
	@Override
	public CardTheme build() {

		CardThemeImpl ret = new CardThemeImpl();

		ret.setDifficulty(this.difficulty);
		ret.setEnabled(true); // TODO implement this
		ret.setExpiryTime(this.expiryTime);
		ret.setId(this.id);
		ret.setName(this.name);
		ret.setPickRate(0); // TODO implement this
		ret.setCoins(this.coins);
		ret.setPublishTime(this.publishTime);
		ret.setScore(this.experience); // TODO implement this
		ret.setTime(this.time);
		ret.setType(this.type);
		ret.setVersion(this.version);

		return ret;
	}

}
