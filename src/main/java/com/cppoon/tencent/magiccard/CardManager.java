/**
 * 
 */
package com.cppoon.tencent.magiccard;

import java.util.Date;

import com.cppoon.tencent.magiccard.CardThemeManager.Builder;

/**
 * 
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface CardManager {

	interface Builder {

		Builder id(int id);

		Builder itemNumber(int itemNumber);

		Builder theme(CardTheme theme);

		Builder name(String name);

		Builder price(double price);

		Builder time(Date when);

		Builder type(int type);

		Builder version(int version);
		
		Card build();

		Builder enabled(boolean enabled);

		Builder pickRate(int pickRate);

	}

	/**
	 * 
	 * 
	 * @return
	 */
	Builder createBuilder();

}
