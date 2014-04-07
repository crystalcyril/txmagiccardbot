/**
 * 
 */
package com.cppoon.tencent.magiccard.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardInfoSynchronizer;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.ExchangeBox;
import com.cppoon.tencent.magiccard.Game;
import com.cppoon.tencent.magiccard.GameMaster;
import com.cppoon.tencent.magiccard.PlayerProfile;
import com.cppoon.tencent.magiccard.api.impl.DesktopSiteJsCardInfoSynchronizer;
import com.cppoon.tencent.magiccard.impl.BasicGameMaster;
import com.cppoon.tencent.magiccard.impl.SimpleCardManager;
import com.cppoon.tencent.magiccard.impl.SimpleCardThemeManager;
import com.cppoon.tencent.magiccard.test.ManualTests;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.impl.DefaultSessionFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.test.TestAccount;
import com.cppoon.tencent.magiccard.vendor.qzapp.test.TestAccount.Account;
import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
@Category(ManualTests.class)
public class GameMasterTest {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	GameMaster gm;
	
	Game game;
	
	Account ac;
	
	SessionFactory sessionFactory = new DefaultSessionFactory();
	
	
	CardThemeManager cardThemeManager;
	
	CardManager cardManager;
	
	CardInfoSynchronizer synchronizer;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		cardThemeManager = new SimpleCardThemeManager();
		cardManager = new SimpleCardManager();
		
		DesktopSiteJsCardInfoSynchronizer synchronizer = new DesktopSiteJsCardInfoSynchronizer();
		synchronizer.setCardManager(cardManager);
		synchronizer.setCardThemeManager(cardThemeManager);
		this.synchronizer = synchronizer;
		
		// syn the card information.
		InputStream is = Resources
				.getResource("com/cppoon/tencent/magiccard/api/test/card_info_v3.js")
				.openStream();
		synchronizer.synchronize(is);
		
		
		sessionFactory = new DefaultSessionFactory();
		
		ac = TestAccount.getDefaultAccount();
		
		BasicGameMaster gm = new BasicGameMaster();
		gm.setSessionFactory(sessionFactory);
		gm.setCardManager(cardManager);
		this.gm = gm;
		
		game = gm.createGame(ac.getUsername(), ac.getPassword());
		game.start();
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
		ac = null;
		game = null;
		gm = null;
		
	}

	/**
	 * 
	 */
	@Test
	@Category(ManualTests.class)
	public void test_Boot_OK() {
		
		// get overview
		PlayerProfile profile = game.getPlayerProfile();
		
		if (profile != null) {
			log.info("player profile:");
			log.info("- nickname: {}", profile.getNickName());
			log.info("- level   : {}", profile.getLevel());
			log.info("- uin     : {}", profile.getUin());
			log.info("- coins   : {}", profile.getCoins());
		}
		
		// do some basic assertion
		assertNotNull("player profile", profile);
		assertTrue("coins should have at least 100, current: " + profile.getCoins(), profile.getCoins() > 100.00);
		assertTrue("coins should have less than 100000, current: " + profile.getCoins(), profile.getCoins() < 100000.00);
		assertTrue("player level should be at least 4, current: " + profile.getLevel(), profile.getLevel() > 4);
		assertTrue("player level should be less than 30, current: " + profile.getLevel(), profile.getLevel() < 30);
		assertEquals("UIN", Long.parseLong(ac.getUsername()), profile.getUin());
		
	}
	
	/**
	 * 
	 */
	@Test
	@Category(ManualTests.class)
	public void test_ExchangeBox_GetStatus() {
		
		// sizing check
		assertTrue("exchange box size should be >= 10", game.getExchangeBox().getSize() >= 10);
		assertTrue("exchange box size should be <= 18", game.getExchangeBox().getSize() <= 18);
		
		// slot check, we iterate all slots.
		List<ExchangeBox.Slot> slots = game.getExchangeBox().getSlots();
		
		// the two API should be in sync.
		assertEquals(game.getExchangeBox().getSize(), slots.size());

		int i = 0;
		for (ExchangeBox.Slot slot : slots) {
			
			Card card = slot.getCard();
			int slotId = slot.getId();
			
			log.info("exchange slot #{}:", i);
			log.info("- ID       : {}", card.getId());
			log.info("- item no. : {}", card.getItemNo());
			log.info("- name     : {}", card.getName());
			log.info("- pick rate: {}", card.getPickRate());
			log.info("- price    : {}", card.getPrice());
			log.info("- formula  : {}", card.getSynthesisFormula());
			log.info("- theme    : {}", (card.getTheme() == null ? "(null)" : 
				card.getTheme().getName() + " (" + card.getTheme().getId() + ")"));
			log.info("- time     : {}", card.getTime());
			log.info("- version  : {}", card.getVersion());
			log.info("- enabled  : {}", card.isEnabled());
			
			i++;
			
		}
		
	}

	/**
	 * 
	 */
	@Test
	@Category(ManualTests.class)
	public void test_ExchangeBox_OK() throws Exception {
		
		// sizing check - the current number of slots (used and unused).
		assertTrue("exchange box size should be >= 10, now " + game.getExchangeBox().getSize(), game.getExchangeBox().getSize() >= 10);
		assertTrue("exchange box size should be <= 18, now " + game.getExchangeBox().getSize(), game.getExchangeBox().getSize() <= 18);
		
		// sizing check - used.
		assertTrue("exchange box size should be > 0", game.getExchangeBox().getUsedCount() >= 0);
		assertTrue("exchange box size should be <= " + game.getExchangeBox().getSize(), 
				game.getExchangeBox().getUsedCount() <= game.getExchangeBox().getSize());
		
		// slot check, we iterate all slots.
		List<ExchangeBox.Slot> slots = game.getExchangeBox().getSlots();
		
		// the two API should be in sync.
		assertEquals(game.getExchangeBox().getUsedCount(), slots.size());

		// iterate the slots.
		printExchangeBoxSlots(slots);
		
		System.out.println("Enter anything to continue...");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		br.close();
		
		
		slots = game.getExchangeBox().getSlots();
		printExchangeBoxSlots(slots);

	}
	
	
	protected void printExchangeBoxSlots(List<ExchangeBox.Slot> slots) {
		
		int i = 0;
		for (ExchangeBox.Slot slot : slots) {
			
			Card card = slot.getCard();
			int slotId = slot.getId();
			
			log.info("exchange slot #{}:", i);
			if (card != null) {
				log.info("- card id  : {}", card.getId());
				log.info("- item no. : {}", card.getItemNo());
				log.info("- name     : {}", card.getName());
				log.info("- pick rate: {}", card.getPickRate());
				log.info("- price    : {}", card.getPrice());
				log.info("- formula  : {}", card.getSynthesisFormula());
				log.info("- theme    : {}", (card.getTheme() == null ? "(null)" : 
					card.getTheme().getName() + " (" + card.getTheme().getId() + ")"));
				log.info("- time     : {}", card.getTime());
				log.info("- version  : {}", card.getVersion());
				log.info("- enabled  : {}", card.isEnabled());
			} else {
				log.info("- (empty)");
			}
			
			i++;
			
		}
		
	}


}
