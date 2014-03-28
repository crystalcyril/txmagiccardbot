/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import java.util.Hashtable;
import java.util.Map;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.impl.internal.SimpleCardManagerBuilder;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SimpleCardManager implements CardManager {
	
	Map<Integer /* card ID */, Card> cards;
	
	/**
	 * 
	 */
	public SimpleCardManager() {
		super();
		
		cards = new Hashtable<Integer, Card>();
	}


	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager#createBuilder()
	 */
	@Override
	public Builder createBuilder() {
		return new SimpleCardManagerBuilder();
	}

	
	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager#findCardById(int)
	 */
	@Override
	public Card findCardById(int cardId) {
		
		return this.cards.get(cardId);
		
	}


	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager#registerCard(com.cppoon.tencent.magiccard.Card)
	 */
	@Override
	public void registerCard(Card card) {
		
		if (card == null) {
			throw new IllegalArgumentException("card should not be null");
		}
		
		cards.put(card.getId(), card);
		
	}
	
}
