/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.impl;

import com.cppoon.tencent.magiccard.CardManager;
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

	CardManager cardManager;
	
	HttpClientFactory httpClientFactory = new HttpComponentsHttpClientFactory();

	@Override
	public Session createSession(String username, String password) {

		SessionImpl session = new SessionImpl(httpClientFactory, username,
				password);
		
		session.setCardManager(cardManager);

		return session;
	}

	public void setHttpClientFactory(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

	/**
	 * @return the cardManager
	 */
	public CardManager getCardManager() {
		return cardManager;
	}

	/**
	 * @param cardManager the cardManager to set
	 */
	public void setCardManager(CardManager cardManager) {
		this.cardManager = cardManager;
	}

}
