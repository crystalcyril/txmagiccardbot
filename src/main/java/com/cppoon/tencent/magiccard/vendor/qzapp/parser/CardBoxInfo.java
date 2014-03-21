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

	public static interface PageLink {
		
		boolean isCurrent();
		
		int getPageNumber();
		
	}

	private List<ExchangeBoxSlot> slots;

	private List<PageLink> pageLinks;
	
	private String safeBoxUrl;
	
	private String exchangeBoxUrl;
	
	private int pages;

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
	 * @return the pageLinks
	 */
	public List<PageLink> getPageLinks() {
		return pageLinks;
	}

	/**
	 * @param pageLinks the pageLinks to set
	 */
	public void setPageLinks(List<PageLink> pageLinks) {
		this.pageLinks = pageLinks;
	}

	/**
	 * @param exchangeBoxUrl the exchangeBoxUrl to set
	 */
	public void setExchangeBoxUrl(String exchangeBoxUrl) {
		this.exchangeBoxUrl = exchangeBoxUrl;
	}

	public int getCurrentPage() {
		if (pageLinks == null) return 0;
		
		for (PageLink link : pageLinks) {
			if (link.isCurrent()) {
				return link.getPageNumber();
			}
		}
		
		return 0;
		
	}
	
}
