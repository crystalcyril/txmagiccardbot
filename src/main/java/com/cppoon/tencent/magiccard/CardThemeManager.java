/**
 * 
 */
package com.cppoon.tencent.magiccard;

import java.util.Date;

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

	Builder createBuilder();

}
