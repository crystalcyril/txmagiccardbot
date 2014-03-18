/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.impl;

import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;
import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionAuthStatus;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SessionImpl implements Session {

	SessionAuthStatus authStatus;

	// credential begins

	String username;

	String password;

	// credential ends

	/**
	 * Builds an instance of session.
	 * 
	 * @param username
	 * @param password
	 */
	public SessionImpl(String username, String password) {
		this.username = username;
		this.password = password;

		reset();
	}

	/**
	 * Reset the internal authentication status.
	 */
	protected void reset() {

		this.authStatus = SessionAuthStatus.UNAUTHENTICATED;

	}

	@Override
	public AccountOverview getAccountOverview() {

		// trigger authenticate if not.
		if (authStatus != SessionAuthStatus.AUTHENTICATED) {
			triggerAuthentication();
		}

		return null;
	}

	protected void triggerAuthentication() {

		
		
	}

	@Override
	public SessionAuthStatus getAuthStatus() {
		return authStatus;
	}

}
