/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser;

import java.util.List;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class CardBoxInfo {

	private List<ExchangeBoxSlot> slots;

	private String safeBoxUrl;
	
	private String exchangeBoxUrl;

	public CardBoxInfo(List<ExchangeBoxSlot> slots) {
		super();

		this.slots = slots;
	}

	public List<ExchangeBoxSlot> getSlots() {
		return slots;
	}

	/**
	 * @return the safeBoxUrl
	 */
	public String getSafeBoxUrl() {
		return safeBoxUrl;
	}

	/**
	 * @param safeBoxUrl
	 *            the safeBoxUrl to set
	 */
	public void setSafeBoxUrl(String safeBoxUrl) {
		this.safeBoxUrl = safeBoxUrl;
	}

	/**
	 * @return the exchangeBoxUrl
	 */
	public String getExchangeBoxUrl() {
		return exchangeBoxUrl;
	}

	/**
	 * @param exchangeBoxUrl the exchangeBoxUrl to set
	 */
	public void setExchangeBoxUrl(String exchangeBoxUrl) {
		this.exchangeBoxUrl = exchangeBoxUrl;
	}
	
}
