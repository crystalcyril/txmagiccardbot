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
	public void testParse_OK() throws Exception {
		
		String html = ParserTestUtil.readResourceAsString("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/mfkp_mainpage_stolen_stove_only-20140319.htm");
		
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

}
