/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.api.StoveStatus;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StolenStove;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.StolenStoveParser20140319;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class StolenStoveParser20140319Test {

	StolenStoveParser20140319 parser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new StolenStoveParser20140319();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		parser = null;
	}

	@Test
	public void testParse_OK_StoveIdle() throws Exception {
		
		String html = ParserTestUtil.readResourceAsString("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/mfkp_mainpage_stolen_stove_only_stove_idle-20140319.htm");
		
		List<StolenStove> stoves = parser.parse(html);
		
		//
		// THEN
		//
		
		assertNotNull("parse result", stoves);
		
		assertEquals("number of stoves", 1, stoves.size());
		
		// check individual stoves
		
		StolenStove si;
		
		// 1st stove
		si = stoves.get(0);
		
		assertNull("card theme name", si.getCardThemeName());
		assertNull("card name", si.getCardName());
		assertEquals("card price", 0, si.getCardPrice(), 0);
		assertEquals("stove status", StoveStatus.IDLE, si.getStatus());
		
	}

	@Test
	public void testParse_OK_StoveSynthesized() throws Exception {
		
		String html = ParserTestUtil.readResourceAsString("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/mfkp_mainpage_stolen_stove_only_stove_synthesized-20140319.htm");
		
		List<StolenStove> stoves = parser.parse(html);
		
		//
		// THEN
		//
		
		assertNotNull("parse result", stoves);
		
		assertEquals("number of stoves", 1, stoves.size());
		
		// check individual stoves
		
		StolenStove si;
		
		// 1st stove
		si = stoves.get(0);
		
		assertEquals("card theme name", "蜻蜓款款飞", si.getCardThemeName());
		assertEquals("card name", "长痣绿蜓", si.getCardName());
		assertEquals("card price", 40, si.getCardPrice(), 0);
		assertEquals("stove status", StoveStatus.SYNTHESIZED, si.getStatus());
		
	}
	
	@Test
	public void testParse_OK_StoveSynthesizing() throws Exception {
		
		String html = ParserTestUtil.readResourceAsString("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/mfkp_mainpage_stolen_stove_only_stolen_stove_synthesizing-20140319.htm");
		
		List<StolenStove> stoves = parser.parse(html);
		
		//
		// THEN
		//
		
		assertNotNull("parse result", stoves);
		
		assertEquals("number of stoves", 1, stoves.size());
		
		// check individual stoves
		
		StolenStove si;
		
		// 1st stove
		si = stoves.get(0);
		
		// 瓢虫物语-茄二十八星瓢虫[40]
		assertEquals("card theme name", "瓢虫物语", si.getCardThemeName());
		assertEquals("card name", "茄二十八星瓢虫", si.getCardName());
		assertEquals("card price", 40, si.getCardPrice(), 0);
		assertEquals("stove status", StoveStatus.SYNTHESIZING, si.getStatus());
		
	}
}
