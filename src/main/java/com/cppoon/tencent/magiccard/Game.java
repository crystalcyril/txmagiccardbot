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

	/**
	 * Returns the exchange box of this game.
	 * 
	 * @return
	 */
	ExchangeBox getExchangeBox();

	/**
	 * Returns the safe box of this game.
	 * 
	 * @return
	 */
	SafeBox getSafeBox();

	/**
	 * Returns all stoves of this game.
	 * 
	 * @return
	 */
	Stoves getStoves();

	/**
	 * Start the engine.
	 */
	void start();

}
