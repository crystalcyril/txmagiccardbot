/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.impl;

import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionFactory;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class DefaultSessionFactory implements SessionFactory {

	@Override
	public Session createSession(String username, String password) {

		SessionImpl session = new SessionImpl(username, password);

		return session;
	}

}
