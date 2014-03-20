/**
 * 
 */
package com.cppoon.tencent.magiccard.api;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public enum StoveStatus {

	/**
	 * No activity is carried by the stove.
	 */
	IDLE,

	/**
	 * The stove is synthesizing something.
	 */
	SYNTHESIZING,

	/**
	 * The stove has finished synthesizing something.
	 */
	SYNTHESIZED

}
