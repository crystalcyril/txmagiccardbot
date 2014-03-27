/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.vendor.qzapp.impl.UrlUtil;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class UrlUtilTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.cppoon.tencent.magiccard.vendor.qzapp.impl.UrlUtil#buildSafeBoxUrl(java.lang.String, int)}.
	 */
	@Test
	public void testBuildSafeBoxUrl_PageOne() {

		String actual = UrlUtil.buildSafeBoxUrl("aBcDeFgH123", 0);
		
		assertEquals("http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=aBcDeFgH123&t=1", actual);
		
	}
	
	/**
	 * Test method for {@link com.cppoon.tencent.magiccard.vendor.qzapp.impl.UrlUtil#buildSafeBoxUrl(java.lang.String, int)}.
	 */
	@Test
	public void testBuildSafeBoxUrl_PageTwo() {

		String actual = UrlUtil.buildSafeBoxUrl("aBcDeFgH123", 1);
		
		assertEquals("http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=aBcDeFgH123&t=1&n=2", actual);
		
	}
	
	/**
	 * Test method for {@link com.cppoon.tencent.magiccard.vendor.qzapp.impl.UrlUtil#buildSafeBoxUrl(java.lang.String, int)}.
	 */
	@Test
	public void testBuildSafeBoxUrl_MissingSid() {

		try {
			
			UrlUtil.buildSafeBoxUrl(null, 1);

			fail("should throw IllegalArgumentException");
			
		} catch (IllegalArgumentException e) {
			// expected
		}
		
	}

	/**
	 * Test method for {@link com.cppoon.tencent.magiccard.vendor.qzapp.impl.UrlUtil#buildSafeBoxUrl(java.lang.String, int)}.
	 */
	@Test
	public void testBuildSafeBoxUrl_NegativePageNumber() {

		try {
			
			UrlUtil.buildSafeBoxUrl("aBcDeFgH123", -1);
			
			fail("should throw IllegalArgumentException");
			
		} catch (IllegalArgumentException e) {
			// expected
		}
		
	}	
	
	@Test
	public void testBuildExchangeBoxUrl_OK() {
		
		String actual = UrlUtil.buildExchangeBoxUrl("aBcDeFgH123");
		
		assertEquals("http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=aBcDeFgH123", actual);
		
	}
	
	@Test
	public void testBuildExchangeBoxUrl_Failed_NullSid() {
		
		try {
			
			UrlUtil.buildExchangeBoxUrl(null);
			
			fail("should throw IllegalArgumentException");
			
		} catch (IllegalArgumentException e) {
			// expected
		}
		
	}
}
