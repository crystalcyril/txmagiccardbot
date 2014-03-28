/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.vendor.qzapp.test.TestAccount.Account;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class TestAccountTest {

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
	public void testGetAccountOK() {

		Account account = TestAccount.getAccount("live");

		assertNotNull(account);

		assertNotNull("username", account.getUsername());
		assertNotNull("password", account.getPassword());

	}

}
