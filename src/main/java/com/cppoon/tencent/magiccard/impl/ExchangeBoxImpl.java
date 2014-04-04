/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.cppoon.tencent.magiccard.ExchangeBox;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.ExchangeBoxSlot;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ExchangeBoxImpl implements ExchangeBox {

	GameImpl game;

	Map<Integer /* slot ID */, ExchangeBox.Slot> slots;
	
	
	private Date lastUpdated;
	
	
	public ExchangeBoxImpl(GameImpl game) {
		
		this.game = game;
		
		slots = new Hashtable<Integer, ExchangeBox.Slot>();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.ExchangeBox#getSize()
	 */
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.ExchangeBox#getUsedCount()
	 */
	@Override
	public int getUsedCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.ExchangeBox#getSlots()
	 */
	@Override
	public List<Slot> getSlots() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Refresh the internal status of this object.
	 */
	protected void refresh() {
		
		if (!isCacheExpired()) return;
		
		List<ExchangeBoxSlot> xchgBoxes = game.session.getExchangeBoxSlots();
		
		synchronize(xchgBoxes);
		
		this.lastUpdated = new Date();
		
	}

	private void synchronize(List<ExchangeBoxSlot> xchgBoxes) {
		
		
		
		
	}

	private boolean isCacheExpired() {
		if (lastUpdated == null) return true;

		if (new Date().getTime() - lastUpdated.getTime() >= 5000) return true;
		
		return false;
	}

}
