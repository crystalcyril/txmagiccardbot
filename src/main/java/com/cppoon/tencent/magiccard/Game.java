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
public interface Game {

	/**
	 * Returns the player profile.
	 * 
	 * @return
	 */
	PlayerProfile getPlayerProfile();

	// Market getMarket();

	ExchangeBox getExchangeBox();

	SafeBox getSafeBox();
	
	

}
