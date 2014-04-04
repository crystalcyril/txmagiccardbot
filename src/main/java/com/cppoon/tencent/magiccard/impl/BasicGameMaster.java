/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import java.util.ArrayList;
import java.util.List;

import com.cppoon.tencent.magiccard.Game;
import com.cppoon.tencent.magiccard.GameMaster;

/**
 * Basic implementation of <strong>GameMaster</strong>.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class BasicGameMaster implements GameMaster {

	List<GameImpl> games;
	
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
		
		this.games.add(impl);
		
		return impl;
	}

}
