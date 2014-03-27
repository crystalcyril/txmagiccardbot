/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp;

import java.util.List;

import com.cppoon.tencent.magiccard.vendor.qzapp.parser.ExchangeBoxSlot;

/**
 * 
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

}
