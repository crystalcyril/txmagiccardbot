/**
 * 
 */
package com.cppoon.tencent.magiccard;

/**
 * Defines a safe box.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface SafeBox {

	/**
	 * Defines a safe box slot.
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
		 * 
		 * @return
		 */
		Card getCard();

		/**
		 * Move the card stored in this slot to the exchange box.
		 */
		void putToExchangeBox();

	}

}
