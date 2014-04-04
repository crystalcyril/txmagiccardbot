/**
 * 
 */
package com.cppoon.tencent.magiccard;

import java.util.List;

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

	/**
	 * Returns the total number of slots in this box. This includes both used
	 * and unused slots.
	 * 
	 * @return the total number of slots in this box.
	 */
	int getSize();

	/**
	 * Returns the number of occupied slots in this box.
	 * 
	 * @return the number of occupied slots in this box.
	 */
	int getUsedCount();

	/**
	 * Obtains a list of slots of this box. The list is <strong>not</strong>
	 * backed by the box.
	 * 
	 * @return
	 */
	List<Slot> getSlots();

}
