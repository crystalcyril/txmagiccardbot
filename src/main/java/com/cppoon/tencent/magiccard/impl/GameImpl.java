/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import com.cppoon.tencent.magiccard.ExchangeBox;
import com.cppoon.tencent.magiccard.Game;
import com.cppoon.tencent.magiccard.PlayerProfile;
import com.cppoon.tencent.magiccard.SafeBox;
import com.cppoon.tencent.magiccard.Stoves;
import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionFactory;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class GameImpl implements Game {
	
	protected String username;
	
	protected String password;
	
	SessionFactory sessionFactory;
	
	Session session;
	
	PlayerProfileImpl playerProfile;
	
	ExchangeBoxImpl exchangeBox;
	
	/**
	 * 
	 */
	public GameImpl(String username, String password) {
		super();
		
		this.username = username;
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Game#getPlayerProfile()
	 */
	@Override
	public PlayerProfile getPlayerProfile() {
		
		// XXX make this thread-safe
		if (playerProfile == null) {
			playerProfile = new PlayerProfileImpl(this);
		}
		
		return playerProfile;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Game#getExchangeBox()
	 */
	@Override
	public ExchangeBox getExchangeBox() {
		
		// XXX make this thread-safe
		if (exchangeBox == null) {
			exchangeBox = new ExchangeBoxImpl(this);
		}
		
		return exchangeBox;
		
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Game#getSafeBox()
	 */
	@Override
	public SafeBox getSafeBox() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Game#getStoves()
	 */
	@Override
	public Stoves getStoves() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.Game#start()
	 */
	@Override
	public void start() {
		
		Session session = sessionFactory.createSession(username, password);
		this.session = session;

	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
