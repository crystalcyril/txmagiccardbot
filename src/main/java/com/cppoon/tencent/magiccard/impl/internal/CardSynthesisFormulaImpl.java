/**
 * 
 */
package com.cppoon.tencent.magiccard.impl.internal;

import java.util.ArrayList;
import java.util.List;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardSynthesisFormula;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class CardSynthesisFormulaImpl implements CardSynthesisFormula {

	Card target;

	long time;

	List<Card> materials;
	
	public CardSynthesisFormulaImpl(Card target, long time, List<Card> materials) {
		
		if (target == null) {
			throw new IllegalArgumentException("target card cannot be null");
		}
		if (time < 0) {
			throw new IllegalArgumentException("time cannot be negative");
		}
		if (materials == null) {
			throw new IllegalArgumentException("material cards cannot be null");
		}
		
		this.target = target;
		this.time = time;
		this.materials = materials;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardSynthesisFormula#getTarget()
	 */
	@Override
	public Card getTarget() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardSynthesisFormula#getTime()
	 */
	@Override
	public long getTime() {
		return time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardSynthesisFormula#getMaterials()
	 */
	@Override
	public List<Card> getMaterials() {
		return new ArrayList<Card>(materials);
	}

}
