/**
 * 
 */
package com.cppoon.tencent.magiccard.test.data;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardTheme;


/**
 * Contains convenient methods to check common test data.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public final class TestDataCard {

	private TestDataCard() {
		super();
	}

	/**
	 * Check the card with ID = 38.
	 * 
	 * @param actualCard
	 * @param theme
	 */
	public static final void assertCard38_1_Star(Card actualCard, CardTheme theme) {
		
		CardDataTestUtil.assertCard(actualCard, 38, theme, "整套搭配", 150.0, 1, 0, true, 1, null, 0);
		
	}
	

}
