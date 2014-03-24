/**
 * 
 */
package com.cppoon.tencent.magiccard.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardTheme;
import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.impl.SimpleCardManager;
import com.cppoon.tencent.magiccard.impl.SimpleCardThemeManager;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class CardManagerTest {

	CardThemeManager cardThemeManager;

	CardManager cardManager;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		cardThemeManager = new SimpleCardThemeManager();
		cardManager = new SimpleCardManager();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		cardThemeManager = null;
		cardManager = null;
	}

	/**
	 * 
	 * [29,40,'城市街道',40,1,0,1,1,0,1027529,[0]],
	 */
	@Test
	public void testAddCard_One() {

		// create a card theme
		CardTheme theme = cardThemeManager.createBuilder().id(40).name("时尚男套装")
				.difficulty(1).publishTime(new Date(1251970023 * 1000)).type(0)
				.version(0).color(0xffffff).time(new Date(1309831119 * 1000))
				.build();

		Card card = cardManager.createBuilder().id(29).theme(theme)
				.name("城市街道").price(40).type(1).version(1).build();

		assertNotNull("created card", card);
		assertEquals("theme", theme, card.getTheme());
		assertEquals("theme", theme.getId(), card.getTheme().getId());

	}

}
