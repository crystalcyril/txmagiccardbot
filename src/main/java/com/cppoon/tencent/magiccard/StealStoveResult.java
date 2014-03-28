/**
 * 
 */
package com.cppoon.tencent.magiccard;

/**
 * Defines the result code for stealing stoves from friends.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public enum StealStoveResult {

	/**
	 * Operation successful.
	 */
	OK,

	/**
	 * The specified target UNI is not a friend of the player.
	 */
	NOT_FRIEND,
	
	/**
	 * No theme is found for the specified card ID.
	 */
	THEME_NOT_FOUND,
	
	/**
	 * The stealing limit has been reached.
	 */
	LIMIT_REACHED,
	
	/**
	 * The response is not known.
	 */
	UNKNOWN_RESPONSE

}
