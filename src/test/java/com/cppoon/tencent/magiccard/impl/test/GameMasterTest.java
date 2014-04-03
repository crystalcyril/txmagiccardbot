/**
 * 
 */
package com.cppoon.tencent.magiccard.impl.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.Game;
import com.cppoon.tencent.magiccard.GameMaster;
import com.cppoon.tencent.magiccard.vendor.qzapp.test.TestAccount;
import com.cppoon.tencent.magiccard.vendor.qzapp.test.TestAccount.Account;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class GameMasterTest {
	
	GameMaster gm;

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

	@Test
	public void test_Boot_OK() {
		
		Account ac = TestAccount.getDefaultAccount();
		
		Game game = gm.createGame(ac.getUsername(), ac.getPassword());
		
		gm.start();
		
		// get overview
		
		
	}

}
