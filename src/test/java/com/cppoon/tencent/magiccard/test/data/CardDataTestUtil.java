/**
 * 
 */
package com.cppoon.tencent.magiccard.test.data;

import static org.junit.Assert.*;

import java.util.Date;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardTheme;

/**
 * 
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public final class CardDataTestUtil {
	
	private CardDataTestUtil() {
		super();
	}

	/**
	 * Check the specified card against a list of expected attribute values.
	 * 
	 * @param actualCard
	 * @param expectedCardId
	 * @param expectedTheme
	 * @param expectedName
	 * @param expectedPrice
	 * @param expectedType
	 * @param expectedPickRate
	 * @param expectedEnabled
	 * @param expectedVersion
	 * @param expectedTime
	 * @param expectedItemNo
	 */
	public static final void assertCard(Card actualCard, 
			int expectedCardId, CardTheme expectedTheme,
			String expectedName,
			double expectedPrice, int expectedType, 
			int expectedPickRate, boolean expectedEnabled,
			int expectedVersion, Date expectedTime,
			int expectedItemNo) {
		
		assertNotNull("card", actualCard);
		
		// check attributes

		assertEquals("card id", expectedCardId, actualCard.getId());
		
		assertNotNull("theme should not be null", actualCard.getTheme());
		assertTrue("theme has same reference", expectedTheme == actualCard.getTheme());
		
		assertEquals("name", expectedName, actualCard.getName());
		assertEquals("price", expectedPrice, actualCard.getPrice(), 0.00);
		assertEquals("type", expectedType, actualCard.getType());
		assertEquals("pick rate", expectedPickRate, actualCard.getPickRate());
		assertEquals("enabled", expectedEnabled, actualCard.isEnabled());
		assertEquals("version", expectedVersion, actualCard.getVersion());
		assertEquals("time", expectedTime, actualCard.getTime());
		assertEquals("item number", expectedItemNo, actualCard.getItemNo());
		
	}
	
	public static void assertCardTheme(CardTheme theme,
			int expectedId, String expectedName, int expectedDifficulty,
			Date expectedPublishTime, int expectedPickRate, 
			boolean expectedEnabled, double expectedCoins, 
			int expectedExperience, String expectedText,
			int expectedType, int expectedVersion,
			Date expectedTime, Date expectedExpiryTime) {
		
		//theme_id, theme_name,theme_Difficulty,theme_PublishTime,theme_PickRate, theme_Enable, theme_Prize,theme_Score,theme_color, gift, text,
		//card1_id,..,cardn_id,theme_type,version,time,offtime,flash_src_tid
		
		assertEquals("theme id", expectedId, theme.getId());
		
		assertNotNull("name", theme.getName());
		assertEquals("name", expectedName, theme.getName());
		
		assertEquals("difficulty", expectedDifficulty, theme.getDifficulty());
		assertEquals("publish time", expectedPublishTime, theme.getPublishTime());
		assertEquals("pick rate", expectedPickRate, theme.getPickRate());
		assertEquals("enabled", expectedEnabled, theme.isEnabled());
		assertEquals("coins", expectedCoins, theme.getCoins(), 0.00);
		assertEquals("bonus experience", expectedExperience, theme.getExperience());
		
		assertEquals("text", expectedText, theme.getText());
		assertEquals("type", expectedType, theme.getType());
		assertEquals("version", expectedVersion, theme.getVersion());
		assertEquals("time", expectedTime, theme.getTime());
		assertEquals("expiry time", expectedExpiryTime, theme.getExpiryTime());
		
	}
	
}
