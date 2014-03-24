/**
 * 
 */
package com.cppoon.tencent.magiccard;

import java.util.List;

/**
 * Defines the API of a card synthesis formula.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface CardSynthesisFormula {

	/**
	 * Returns the target card which this formula will synthesize.
	 * 
	 * @return the target card synthesized. Never be <code>null</code>.
	 */
	Card getTarget();

	/**
	 * Returns the time, in seconds, which the target card is required to be
	 * completely synthesized.
	 * 
	 * @return the time to synthesize the target card in seconds.
	 */
	long getTime();

	/**
	 * Returns the material cards required to synthesize the target card.
	 * 
	 * @return the list of cards, never be <code>null</code> or empty.
	 */
	List<Card> getMaterials();

}
