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
public interface CardThemeType {

	/**
	 * Ordinary card theme.
	 * <p>
	 * 
	 * Most of the card themes fall into this category.
	 */
	public static final int NORMAL = 0;

	/**
	 * The theme is expired.
	 */
	public static final int EXPIRED = 1;

	/**
	 * Special theme which cards can only be obtained by drawing from the card
	 * deck (or by transformation).
	 */
	public static final int DRAW = 2;

	/**
	 */
	public static final int UNKNONW_5 = 5;

	/**
	 * Flash card (閃卡).
	 */
	public static final int FLASH = 9;

}
