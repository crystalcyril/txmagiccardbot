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
public interface CardManager {

	interface Builder {

		Builder id(int id);

		Builder theme(CardTheme theme);

		Builder name(String name);

		Builder price(double price);

		Builder time(Date when);

		Builder type(int type);

		Builder version(int version);
		
		Card build();

	}

	/**
	 * 
	 * 
	 * @return
	 */
	Builder createBuilder();

}
