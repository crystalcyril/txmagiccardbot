/**
 * 
 */
package com.cppoon.tencent.magiccard.impl.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardSynthesisFormula;
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
	 * Test to create one card.
	 * 
	 * [29,40,'城市街道',40,1,0,1,1,0,1027529,[0]],
	 */
	@Test
	public void testCreateCard_CreateOne() {

		// create a card theme
		CardTheme theme = buildDefaultTestTheme();

		Card card = cardManager.createBuilder().id(29).theme(theme)
				.name("城市街道").price(40).type(1).version(1).build();

		assertNotNull("created card", card);

		assertEquals("theme", theme, card.getTheme());
		assertEquals("theme", theme.getId(), card.getTheme().getId());

		// card has no synthesis formula
		assertNull("card synthesis formula", card.getSynthesisFormula());
		assertEquals("card synthesis formula", 29, card.getId());
		assertEquals("item number", 0, card.getItemNo());
		assertEquals("name", "城市街道", card.getName());
		assertEquals("pick rate", 0, card.getPickRate());
		assertEquals("price", 40.0, card.getPrice(), 0.00);
		assertEquals("pick rate", 0, card.getPickRate());
		assertNull("synthesis formulat", card.getSynthesisFormula());
		assertNull("time", card.getTime());
		assertEquals("type", 1, card.getType());
		assertEquals("version", 1, card.getVersion());
		
		// the theme should now consist of 4 cards.
		
	}

	/**
	 * We made the following virtual card suit.
	 * 
	 */
	@Test
	public void testAddSynthesisRule() {

		CardTheme theme = buildDefaultTestTheme();

		Card card_40price = cardManager.createBuilder().id(35).theme(theme)
				.name("酷帅打扮").price(40).type(1).version(1).build();
		// card IDs: 30,31,33
		// 30: 搞怪笑脸
		Card card_id_30 = cardManager.createBuilder().id(30).theme(theme)
				.name("搞怪笑脸").price(10).type(1).version(1).build();
		Card card_id_31 = cardManager.createBuilder().id(31).theme(theme)
				.name("韩版长发").price(10).type(1).version(1).build();
		Card card_id_33 = cardManager.createBuilder().id(33).theme(theme)
				.name("黑框大镜").price(10).type(1).version(1).build();

		// WHEN
		card_40price.setSynthesisFormula(3600, new Card[] { card_id_30,
				card_id_31, card_id_33 });

		// THEN
		CardSynthesisFormula formula = card_40price.getSynthesisFormula();
		assertNotNull("synthesis formula", formula);

		assertEquals("synthesis time", formula.getTime(), 3600);
		List<Card> actualMaterialCards = formula.getMaterials();
		assertNotNull("material cards", actualMaterialCards);
		assertEquals("number of material cards in formula", 3,
				actualMaterialCards.size());
		assertEquals("material card 1", card_id_30, actualMaterialCards.get(0));
		assertEquals("material card 2", card_id_31, actualMaterialCards.get(1));
		assertEquals("material card 3", card_id_33, actualMaterialCards.get(2));
		assertNotEquals("material card 1 is not", card_id_31,
				actualMaterialCards.get(0));

	}

	protected CardTheme buildDefaultTestTheme() {
		return cardThemeManager.createBuilder().id(40).name("时尚男套装")
				.difficulty(1).publishTime(new Date(1251970023 * 1000)).type(0)
				.version(0).color(0xffffff).time(new Date(1309831119 * 1000))
				.build();
	}

}
