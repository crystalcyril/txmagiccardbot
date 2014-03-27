/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp;

import java.util.List;

import com.cppoon.tencent.magiccard.StealStoveResult;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.ExchangeBoxSlot;

/**
 * Defines the API which represents a low-level session to a player's account of
 * the game.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface Session {

	/**
	 * Obtains account overview.
	 * 
	 * @return the account status, this will never return <code>null</code>.
	 */
	AccountOverview getAccountOverview();

	/**
	 * Returns the authentication status.
	 * 
	 * @return the authentication status.
	 */
	SessionAuthStatus getAuthStatus();

	/**
	 * Obtains a list of slots in the safe box.
	 * 
	 * @return a list of slots in the safe box. This will never return
	 *         <code>null</code>.
	 */
	List<ExchangeBoxSlot> getSafeBoxSlots();

	/**
	 * Obtains a list of slots in the exchange box.
	 * 
	 * @return a list of slots in the exchange box. This will never return
	 *         <code>null</code>.
	 */
	List<ExchangeBoxSlot> getExchangeBoxSlots();

	/**
	 * Steal the specified friend's stove to synthesize the specified card ID.
	 * 
	 * @param targetUin
	 *            the UIN of the player which the stove will be stolen.
	 * @param targetCardId
	 *            the card ID which to be synthesized.
	 * @return the result of the operation.
	 */
	StealStoveResult stealStove(int targetUin, int targetCardId);

}
