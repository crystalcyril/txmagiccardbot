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

		Builder coins(double prize);
		
		Builder version(int version);

		Builder color(int color);
		
		Builder text(String text);
		
		Builder time(Date when);

		Builder expiryTime(Date when);
		
		Builder experience(int experience);
		
		Builder enabled(boolean enabled);

		Builder pickRate(int pickRate);

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

	/**
	 * Find theme by ID.
	 * 
	 * @param id
	 * @return
	 */
	com.cppoon.tencent.magiccard.CardTheme findThemeById(int id);

	/**
	 * Register card theme with matching ID.
	 * 
	 * @param cardTheme
	 */
	void registerTheme(com.cppoon.tencent.magiccard.CardTheme cardTheme);

}
