/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.vendor.qzapp.parser.LoginForm;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.LoginPageParser20140319;
import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class LoginPageParser20140319Test {

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
	public void testParse_OK() throws Exception {
		
		LoginPageParser20140319 parser = new LoginPageParser20140319();

		String html = Resources
				.toString(
						Resources
								.getResource("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/qzone_login_page-20140318.html"),
						Charset.forName("UTF-8"));
		
		LoginForm form = parser.parse(html);
		
		//
		// verification time.
		//
		assertNotNull("login form", form);
		assertEquals("form submit URL", "http://pt.3g.qq.com/qzoneLogin?sid=AQvjsvF0qiIZLJDllirvCYkR&aid=nLoginqz&vdata=9BAEF13834F34227C46A75A90FAE5D8B", form.getFormSubmitUrl());
		assertEquals("login URL", "http://pt.3g.qq.com/s?aid=nLoginqz&KqqWap_Act=3&sid=AQvjsvF0qiIZLJDllirvCYkR", form.getLoginUrl());
		assertEquals("go URL", "http://qzone.z.qq.com/index.jsp", form.getGoUrl());
		assertEquals("sidtype", "1", form.getSidType());
		assertEquals("aid", "nLoginqz", form.getAid());
		
	}


	@Test
	public void testParse_Fail_MissingFormHiddenValue() throws Exception {
		
		LoginPageParser20140319 parser = new LoginPageParser20140319();

		String html = Resources
				.toString(
						Resources
								.getResource("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/qzone_login_page-20140318-missing-attribute.html"),
						Charset.forName("UTF-8"));
		
		LoginForm form = parser.parse(html);
		
		//
		// verification time.
		//
		assertNull("login form", form);
		
	}
}
