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
	 * @return
	 */
	AccountOverview getAccountOverview();

	/**
	 * Returns the authentication status.
	 * 
	 * @return
	 */
	SessionAuthStatus getAuthStatus();

	/**
	 * Obtains a list of cards in the safe box.
	 * 
	 * @return
	 */
	List<ExchangeBoxSlot> getSafeBoxCards();

}
