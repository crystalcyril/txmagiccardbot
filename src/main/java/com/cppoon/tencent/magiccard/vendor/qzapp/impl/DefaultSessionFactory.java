/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.impl;

import com.cppoon.tencent.magiccard.http.client.HttpClientFactory;
import com.cppoon.tencent.magiccard.http.client.HttpComponentsHttpClientFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionFactory;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class DefaultSessionFactory implements SessionFactory {

	HttpClientFactory httpClientFactory = new HttpComponentsHttpClientFactory();

	@Override
	public Session createSession(String username, String password) {

		SessionImpl session = new SessionImpl(httpClientFactory, username,
				password);

		return session;
	}

	public void setHttpClientFactory(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

}
