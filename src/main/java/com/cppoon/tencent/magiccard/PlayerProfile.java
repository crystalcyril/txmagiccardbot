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
public interface PlayerProfile {

	/**
	 * Returns the nick name of the player.
	 * 
	 * @return
	 */
	String getNickName();

	/**
	 * Returns the UIN (QQ number) of this account.
	 * 
	 * @return
	 */
	long getUin();

	/**
	 * Returns the current level of the player in this game.
	 * <p>
	 * 
	 * The level starts at 1.
	 * 
	 * @return
	 */
	int getLevel();

	/**
	 * Returns the coins the player has.
	 * 
	 * @return
	 */
	double getCoins();

}
