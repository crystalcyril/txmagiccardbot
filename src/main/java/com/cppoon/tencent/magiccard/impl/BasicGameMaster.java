/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import java.util.ArrayList;
import java.util.List;

import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.Game;
import com.cppoon.tencent.magiccard.GameMaster;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionFactory;

/**
 * Basic implementation of <strong>GameMaster</strong>.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class BasicGameMaster implements GameMaster {

	List<GameImpl> games;
	
	SessionFactory sessionFactory;
	
	CardManager cardManager;
	
	/**
	 * 
	 */
	public BasicGameMaster() {
		super();
		
		games = new ArrayList<GameImpl>();
		
	}

	@Override
	public Game createGame(String username, String password) {
		
		// TODO cyril 2014-04-04 do duplicate check.
		
		GameImpl impl = new GameImpl(username, password);
		impl.setSessionFactory(sessionFactory);
		impl.setCardManager(cardManager);
		
		this.games.add(impl);
		
		return impl;
		
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @param cardManager the cardManager to set
	 */
	public void setCardManager(CardManager cardManager) {
		this.cardManager = cardManager;
	}
	
}
