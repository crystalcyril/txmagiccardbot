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
	public static final void assertCard38_1_Star(Card actualCard,
			CardTheme theme) {

		CardDataTestUtil.assertCard(actualCard, 38, theme, "整套搭配", 150.0, 1, 0,
				true, 1, null, 0);

	}

	/**
	 * Returns the total number of cards in the card info javascript file.
	 * 
	 * @return
	 */
	public static final int getTotalCardsInCardInfoJsV3() {
		return 4816;
	}

	/**
	 * Returns the total number of themes in the card info javascript file.
	 * 
	 * @return
	 */
	public static final int getTotalThemesInCardInfoJsV3() {
		return 255;
	}

	/**
	 * Returns the total number of themes in the card info javascript file.
	 * 
	 * @return
	 */
	public static final int getTotalComposeRulesInCardInfoJsV3() {
		return 3087;
	}
	
}
