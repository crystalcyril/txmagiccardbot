/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public abstract class TestAccount {

	public static interface Account {

		String getUsername();

		String getPassword();

	}

	private static class AccountImpl implements Account {

		private String username;

		private String password;

		public AccountImpl(String username, String password) {
			this.username = username;
			this.password = password;
		}

		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		@Override
		public String toString() {
			return "username=" + username + ", password=" + password;
		}
	}

	private static final String ACCOUNT_PROPERTIES_FILE = "com/cppoon/tencent/magiccard/test/data/test_accounts.properties";
	
	/**
	 * The default account.
	 */
	private static final Account DEFAULT_ACCOUNT = new AccountImpl(
			"2904233460", "qqmagiccard169_");

	/**
	 * Returns the test account's user name.
	 * 
	 * @return
	 */
	public static final String getUsername() {
		return getDefaultAccount().getUsername();
	}

	/**
	 * Returns the test account's password.
	 * 
	 * @return
	 */
	public static final String getPassword() {
		return getDefaultAccount().getPassword();
	}

	/**
	 * Returns the default account to use.
	 * 
	 * @return
	 */
	public static final Account getDefaultAccount() {
		return DEFAULT_ACCOUNT;
	}
	
	/**
	 * Obtains account based on the given profile.
	 * <p>
	 * 
	 * This method will read the file stored in the class path
	 * "com/cppoon/tencent/magiccard/test/data/test_data.properties", using
	 * property keys:
	 * 
	 * <ul>
	 * <li>Username: &lt;profile&gt;.username</li>
	 * <li>Password: &lt;profile&gt;.password</li>
	 * </ul>
	 * 
	 * Since password is sensitive so should not be committed to repository,
	 * the file is excluded from the source repository. Please copy the file
	 * <strong>test_data.properties-dist</strong> and renamed to 
	 * <strong>test_data.properties</strong>.
	 * 
	 * @param profile
	 * @return
	 */
	public static final Account getAccount(String profile) {
		
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(ACCOUNT_PROPERTIES_FILE);
		if (is == null) {
			fail("properties file '" + ACCOUNT_PROPERTIES_FILE
					+ "' cannot be found, check copy the file "
					+ ACCOUNT_PROPERTIES_FILE + "-dist and edit the file");
		}
		
		Properties props = new Properties();
		try {
			// load properties file.
			props.load(is);
			
			String usernameKey = profile + ".username";
			String passwordKey = profile + ".password";
			
			String username = props.getProperty(usernameKey);
			String password = props.getProperty(passwordKey);

			if (username == null) {
				fail("the key " + usernameKey + " cannot be found in file " + ACCOUNT_PROPERTIES_FILE);
			}
			if (password == null) {
				fail("the key " + passwordKey + " cannot be found in file " + ACCOUNT_PROPERTIES_FILE);
			}
			
			return new AccountImpl(username, password);
			
		} catch (IOException e) {
			e.printStackTrace();
			fail("failed to load file " + ACCOUNT_PROPERTIES_FILE + ", see stack trace");
			return null;
		}
		
	}

}
