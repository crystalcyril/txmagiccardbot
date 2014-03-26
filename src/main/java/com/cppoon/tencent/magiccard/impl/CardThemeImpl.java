/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardSynthesisFormula;
import com.cppoon.tencent.magiccard.CardTheme;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class CardThemeImpl implements CardTheme {

	private int id;

	private String name;

	private int difficulty;

	private int pickRate;

	private double coins;

	private Date publishTime;

	private int experience;

	private String text;

	private Date time;

	private Date expiryTime;

	private int type;

	private int version;

	private boolean enabled;

	Set<Card> cards;

	public CardThemeImpl() {
		super();

		cards = new HashSet<Card>();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty
	 *            the difficulty to set
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the pickRate
	 */
	public int getPickRate() {
		return pickRate;
	}

	/**
	 * @param pickRate
	 *            the pickRate to set
	 */
	public void setPickRate(int pickRate) {
		this.pickRate = pickRate;
	}

	/**
	 * @return the prize
	 */
	public double getCoins() {
		return coins;
	}

	/**
	 * 
	 * @param coins
	 *            the prize to set
	 */
	public void setCoins(double coins) {
		this.coins = coins;
	}

	/**
	 * @return the publishTime
	 */
	public Date getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime
	 *            the publishTime to set
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	/**
	 * @return the bonus experience given when the theme is completely
	 *         synthesized.
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * @param experience
	 *            the score to set
	 */
	public void setExperience(int experience) {
		this.experience = experience;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the expiryTime
	 */
	public Date getExpiryTime() {
		return expiryTime;
	}

	/**
	 * @param expiryTime
	 *            the expiryTime to set
	 */
	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardTheme#getChildrenCards()
	 */
	@Override
	public List<Card> getChildrenCards() {

		// find all cards which are NOT required by other cards.
		// iterate all synthesis formulas.
		//
		// however, there are cases which all cards in a theme requires NO
		// synthesis.
		//
		// so, this finally breaks down to:
		// - any card which are NOT a synthesis material of any card are
		// considered as top cards.

		// Step 1: Find all card IDs which ARE NOT top cards.
		Set<Integer> extractIds = new HashSet<Integer>();
		for (Card card : cards) {

			CardSynthesisFormula formula = card.getSynthesisFormula();

			// this card requires no synthesis. MAYBE a top card.
			if (formula == null) {
				continue;
			}

			// otherwise, any material cards in the formula ARE NOT
			// top cards.
			for (Card material : formula.getMaterials()) {
				extractIds.add(material.getId());
			}
		}

		// Step 2: Find out the top cards
		ArrayList<Card> ret = new ArrayList<Card>();
		for (Card card : cards) {
			if (!extractIds.contains(card.getId())) {
				ret.add(card);
			}
		}

		return ret;
	}

	public void addCard(CardImpl card) {

		this.cards.add(card);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardTheme#getCardById(int)
	 */
	@Override
	public Card getCardById(int cardId) {

		for (Card card : cards) {
			if (card.getId() == cardId)
				return card;
		}

		return null; // not found
	}
	
	
	
	
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", difficulty=" + difficulty
				+ ", pickRate=" + pickRate + ", coins=" + coins
				+ ", publishTime=" + publishTime + ", score=" + experience
				+ ", time=" + time + ", expiryTime=" + expiryTime + ", type="
				+ type + ", version=" + version + ", enabled=" + enabled;
	}

}
