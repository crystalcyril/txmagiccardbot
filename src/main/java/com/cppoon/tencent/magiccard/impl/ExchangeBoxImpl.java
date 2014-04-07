/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.ExchangeBox;
import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.ExchangeBoxSlot;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ExchangeBoxImpl implements ExchangeBox {
	
	private class BoxImpl implements ExchangeBox.Slot {
		
		private int id;
		
		private Card card;
		
		/**
		 * 
		 */
		public BoxImpl(int id) {
			super();
			
			this.id = id;
		}

		/* (non-Javadoc)
		 * @see com.cppoon.tencent.magiccard.ExchangeBox.Slot#getId()
		 */
		@Override
		public int getId() {
			return id;
		}

		/* (non-Javadoc)
		 * @see com.cppoon.tencent.magiccard.ExchangeBox.Slot#getCard()
		 */
		@Override
		public Card getCard() {
			return card;
		}

		/* (non-Javadoc)
		 * @see com.cppoon.tencent.magiccard.ExchangeBox.Slot#putToSafeBox()
		 */
		@Override
		public void putToSafeBox() {
			throw new UnsupportedOperationException("implements me");
		}

		/* (non-Javadoc)
		 * @see com.cppoon.tencent.magiccard.ExchangeBox.Slot#sell()
		 */
		@Override
		public void sell() {
			throw new UnsupportedOperationException("implements me");
		}

		public void setCard(Card card) {
			this.card = card;
		}
		
	}
	

	GameImpl game;

	Map<Integer /* slot ID */, BoxImpl> slots;
	
	private int slotCount;
	
	CardManager cardManager;
	
	private Date lastUpdated;
	
	
	public ExchangeBoxImpl(GameImpl game) {
		
		this.game = game;
		
		slots = new Hashtable<Integer, BoxImpl>();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.ExchangeBox#getSize()
	 */
	@Override
	public int getSize() {
		refresh();
		
		return slotCount;
	}
	
	/**
	 * @param slotCount the slotCount to set
	 */
	public void setSlotCount(int slotCount) {
		this.slotCount = slotCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.ExchangeBox#getUsedCount()
	 */
	@Override
	public int getUsedCount() {
		refresh();
		
		int n = 0;
		for (BoxImpl box : slots.values()) {
			if (box.getCard() != null) {
				n++;
			}
		}
		
		return n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.ExchangeBox#getSlots()
	 */
	@Override
	public List<Slot> getSlots() {
		
		refresh();
		
		return new ArrayList<Slot>(slots.values());
	}
	
	
	/**
	 * Refresh the internal status of this object.
	 */
	protected void refresh() {
		
		if (!isCacheExpired()) return;
		
		// sync the exchange boxes
		List<ExchangeBoxSlot> xchgBoxes = game.session.getExchangeBoxSlots();
		synchronize(xchgBoxes);
		
		// sync the total slots.
		synchronizeSlotCount();
		
		this.lastUpdated = new Date();
		
	}

	private void synchronize(List<ExchangeBoxSlot> xchgBoxes) {
		
		// iterate the input boxes and compare with our internal boxes
		// - if a new slot ID is found, add it.
		// - if a matching slot ID is found, update it.
		//
		// next step, there may be internal boxes which are not in the
		// input boxes. That means those slots are idle.
		// - update those boxes with status = empty.
		
		Set<Integer> obsoleted = new HashSet<Integer>(this.slots.keySet());
		
		// add or update existing slots
		for (ExchangeBoxSlot xchgBox : xchgBoxes) {
			
			if (obsoleted.contains(xchgBox.getSlotId())) {
				// existing slot found, update it.
				
				BoxImpl box = this.slots.get(xchgBox.getSlotId());
				
				// update internal state
				if (xchgBox.getCardId() != 0) {
					
					Card card = cardManager.findCardById(xchgBox.getCardId());
					box.setCard(card);
					
				}
				
				obsoleted.remove(xchgBox.getSlotId());
				
			} else {
				
				// this is a new slot
				
				BoxImpl box = new BoxImpl(xchgBox.getSlotId());
				
				// update internal state
				if (xchgBox.getCardId() != 0) {
					Card card = cardManager.findCardById(xchgBox.getCardId());
					box.setCard(card);
				}
				
				// save this
				this.slots.put(box.getId(), box);
				
			}
			
		}
		
		if (!obsoleted.isEmpty()) {
			
			for (int slotId : obsoleted) {
				
				BoxImpl box = this.slots.get(slotId);
				box.setCard(null);
				
			}
			
		}
		
	}
	
	protected void synchronizeSlotCount() {
		
		AccountOverview ov = game.session.getAccountOverview();
		if (ov != null) {
			this.slotCount = ov.getCardExchangeBoxSize();
		}
		
	}

	private boolean isCacheExpired() {
		if (lastUpdated == null) return true;

		// XXX 2014-04-07 cyril find a better way.
		if (new Date().getTime() - lastUpdated.getTime() >= 5000) return true;
		
		return false;
	}

	/**
	 * @param cardManager the cardManager to set
	 */
	public void setCardManager(CardManager cardManager) {
		this.cardManager = cardManager;
	}
	
}
