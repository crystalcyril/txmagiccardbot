/**
 * 
 */
package com.cppoon.tencent.magiccard;

import com.cppoon.tencent.magiccard.api.StoveStatus;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface Stove {

	/**
	 * Cancel any card synthesis on this stove.
	 */
	void cancel();

	/**
	 * Checks whether the card synthesis can be cancelled.
	 * 
	 * @return
	 */
	boolean isCancellable();

	/**
	 * Synthesize the specified card.
	 * 
	 * @param card
	 */
	void synthesize(Card card);

	/**
	 * Returns the status of this stove.
	 * 
	 * @return
	 */
	StoveStatus getStatus();

	/**
	 * Returns the card in this stove, if any.
	 * <p>
	 * 
	 * If there is no card in the stove, this method returns <code>null</code>.
	 */
	Card getCard();

}
