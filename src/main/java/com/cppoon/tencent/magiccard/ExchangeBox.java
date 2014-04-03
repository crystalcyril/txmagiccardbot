/**
 * 
 */
package com.cppoon.tencent.magiccard;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface ExchangeBox {

	/**
	 * 
	 * 
	 * @author Cyril
	 * @since 0.1.0
	 */
	interface Slot {

		/**
		 * Returns the unique ID of this slot.
		 * 
		 * @return
		 */
		int getId();

		/**
		 * Returns the card stored in this stove.
		 * <p/>
		 * 
		 * If no card is stored in this slot, <code>null</code> will be
		 * returned.
		 * 
		 * @return
		 */
		Card getCard();

		/**
		 * Put the card to the safe box.
		 */
		void putToSafeBox();

		/**
		 * Sell the card stored in this slot.
		 */
		void sell();

	}

}
