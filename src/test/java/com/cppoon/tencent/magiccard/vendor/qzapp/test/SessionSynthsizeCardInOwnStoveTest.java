/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.StealStoveResult;
import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.impl.DefaultSessionFactory;

/**
 * Session test on synthesizing card in owned stoves.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SessionSynthsizeCardInOwnStoveTest {

	
	
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
	 * 
	 */
	@Test
	public void testStealStove_001_Fail_NonFriend() {
		
		String username = TestAccount.getUsername();
		String password = TestAccount.getPassword();
		
		SessionFactory sm = new DefaultSessionFactory();
		Session session = sm.createSession(username, password);
		
		int targetUin = 383664207;
		int targetCardId = 40;
		
		StealStoveResult result = session.stealStove(targetUin, targetCardId);
		
		assertEquals("steal result", StealStoveResult.NOT_FRIEND, result);
		
		
	}

}
