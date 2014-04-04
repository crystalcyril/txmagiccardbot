/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cppoon.tencent.magiccard.PlayerProfile;
import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class PlayerProfileImpl implements PlayerProfile {

	Logger log = LoggerFactory.getLogger(getClass());
	
	GameImpl game;

	private String nickName;

	private int level;

	private long uin;

	private double coins;

	Date lastUpdated = null;

	/**
	 * 
	 */
	public PlayerProfileImpl(GameImpl game) {
		super();

		this.game = game;

	}

	@Override
	public String getNickName() {

		refreshStatus();

		return nickName;
	}

	@Override
	public long getUin() {

		refreshStatus();

		return uin;
	}

	@Override
	public int getLevel() {

		refreshStatus();

		return level;
	}

	@Override
	public double getCoins() {

		refreshStatus();

		return coins;
	}

	/**
	 * Refresh the internal status of this object.
	 */
	protected void refreshStatus() {

		if (!requireUpdate())
			return;

		AccountOverview ov = game.session.getAccountOverview();
		if (ov != null) {
			this.coins = ov.getCoins();
			this.level = ov.getPlayerLevel();
			
			this.lastUpdated = new Date();
		}

	}

	private boolean requireUpdate() {

		// if the value is never update, return true.
		if (lastUpdated == null) {
			log.trace("cache not expired");
			return true;
		}

		// if the account information is still fresh, don't update.
		if (new Date().getTime() - lastUpdated.getTime() < getCacheExpiryTime())
			return false;
		
		return true;
	}

	/**
	 * Returns the number of milliseconds the cached information is considered
	 * expired.
	 * 
	 * @return
	 */
	protected long getCacheExpiryTime() {
		return 5000;
	}

}
