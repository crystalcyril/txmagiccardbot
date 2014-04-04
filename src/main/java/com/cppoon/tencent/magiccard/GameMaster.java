/**
 * 
 */
package com.cppoon.tencent.magiccard;

/**
 * Defines the interface of a game master. A game master manage instances of
 * games.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface GameMaster {

	/**
	 * Create an instance of game.
	 * 
	 * @param username
	 *            the user name of the account
	 * @param password
	 *            the corresponding password of the username.
	 * @return
	 */
	Game createGame(String username, String password);

}
