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
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StoveInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.StoveParser20140320;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class StoveParser20140320Test {

	StoveParser20140320 parser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new StoveParser20140320();
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

		String html = ParserTestUtil
				.readResourceAsString("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/mfkp_mainpage_stove_only_synthesized_and_idle-20140319.htm");

		//
		// WHEN
		//
		List<StoveInfo> stoves = parser.parse(html);

		//
		// THEN
		//
		assertNotNull("returned result should not be null", stoves);

		// check stove by stove
		StoveInfo si;

		// slot 1
		si = stoves.get(0);	// 0-based
		assertEquals("card theme name", "中国古钱币", si.getCardThemeName());
		assertEquals("card name", "刀币", si.getCardName());
		assertEquals("card price", 168, si.getCardPrice(), 0);
		assertEquals("synthsize status", StoveStatus.SYNTHESIZED, si.getStatus());
		assertEquals("slot ID", 0, si.getSlotId());
		
		// slot 2
		si = stoves.get(1);
		assertEquals("card theme name", "瓢虫物语", si.getCardThemeName());
		assertEquals("card name", "茄二十八星瓢虫", si.getCardName());
		assertEquals("card price", 40, si.getCardPrice(), 0);
		assertEquals("synthsize status", StoveStatus.SYNTHESIZED, si.getStatus());
		assertEquals("slot ID", 1, si.getSlotId());

		// slot 3
		si = stoves.get(2);
		assertNull("card theme name", si.getCardThemeName());
		assertNull("card name", si.getCardName());
		assertEquals("card price", 0, si.getCardPrice(), 0);
		assertEquals("synthsize status", StoveStatus.IDLE, si.getStatus());
		assertEquals("slot ID", 2, si.getSlotId());
		
		
		assertEquals("number of parsed stoves", 4, stoves.size());


	}

}
