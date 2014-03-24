/**
 * 
 */
package com.cppoon.tencent.magiccard;

import java.util.Date;
import java.util.Set;

/**
 * 
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface CardThemeManager {

	/**
	 * Defines the internal builder API.
	 */
	interface Builder {

		Builder id(int id);

		Builder name(String name);

		Builder difficulty(int difficulty);

		Builder publishTime(Date when);

		Builder type(int type);

		Builder version(int version);

		Builder color(int color);

		Builder time(Date when);

		Builder expiryTime(Date when);

		/**
		 * Build the theme.
		 * 
		 * @return
		 */
		CardTheme build();

	}

	/**
	 * Create an instance of builder which can build a theme.
	 * 
	 * @return an instance of builder which can build a theme.
	 */
	Builder createBuilder();

	/**
	 * Returns all themes known to this card theme manager.
	 * 
	 * @return all themes known by this manager. Returns an empty list if no
	 *         theme is known.
	 */
	Set<CardTheme> getAllThemes();

}
