/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardBoxInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardBoxInfo.PageLink;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.ExchangeBoxSlot;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ExchangeCardBoxParser20140320 {

	Pattern pSafeBoxUrl;

	Pattern pExchangeBoxUrl;
	
	
	private static final class PageLinkImpl implements CardBoxInfo.PageLink {

		boolean current = false;
		
		int pageNumber = 0;

		/**
		 * @return the current
		 */
		public boolean isCurrent() {
			return current;
		}

		/**
		 * @param current the current to set
		 */
		public void setCurrent(boolean current) {
			this.current = current;
		}

		/**
		 * @return the pageNumber
		 */
		public int getPageNumber() {
			return pageNumber;
		}

		/**
		 * @param pageNumber the pageNumber to set
		 */
		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}

	}
	
	
	public CardBoxInfo parse(String html) {

		return doParse(html);

	}

	protected CardBoxInfo doParse(String html) {

		// parse slots
		List<ExchangeBoxSlot> slots = parseSlots(html);

		CardBoxInfo ret = new CardBoxInfo(slots);

		// parse safe box URL.
		parseSafeBoxUrl(ret, html);
		
		// parse exchange box URL
		parseExchangeBoxUrl(ret, html);
		
		parsePageLink(ret);

		return ret;

	}

	protected List<ExchangeBoxSlot> parseSlots(String html) {

		SlotParser p = new SlotParser(html);

		return p.parse();
	}

	protected boolean parseSafeBoxUrl(CardBoxInfo ret, String html) {

		Matcher m = getSafeBoxUrlPattern().matcher(html);

		if (!m.find()) {
			return false;
		}

		String href = m.group(1);
		href = StringEscapeUtils.unescapeHtml4(StringUtils.trim(href));
		ret.setSafeBoxUrl(href);

		return true;

	}
	
	protected boolean parseExchangeBoxUrl(CardBoxInfo ret, String html) {

		Matcher m = getExchangeBoxUrlPattern().matcher(html);

		if (!m.find()) {
			return false;
		}

		String href = m.group(1);
		href = StringEscapeUtils.unescapeHtml4(StringUtils.trim(href));
		ret.setExchangeBoxUrl(href);

		return true;

	}

	protected Pattern getSafeBoxUrlPattern() {
		if (pSafeBoxUrl == null) {
			// pSafeBoxUrl =
			// Pattern.compile("<a\\s+href=\"(.*?)\"\\s*>保险箱</a>\\s*(\\s*(\\d+)\\s*/\\s*(\\d+))",
			// Pattern.DOTALL);
			pSafeBoxUrl = Pattern.compile(
					"<a\\s+href=\"([^\\s]+?)\"\\s*>保险箱</a>", Pattern.DOTALL);
		}
		return pSafeBoxUrl;
	}
	
	protected Pattern getExchangeBoxUrlPattern() {
		if (pExchangeBoxUrl == null) {
			pExchangeBoxUrl = Pattern.compile(
					"<a\\s+href=\"([^\\s]+?)\"\\s*>换卡箱</a>", Pattern.DOTALL);
		}
		return pExchangeBoxUrl;
	}

	protected void parsePageLink(CardBoxInfo ret) {
		
		PageLinkImpl plink = new PageLinkImpl();
		plink.setCurrent(true);
		plink.setPageNumber(0);
		
		ArrayList<PageLink> pageLinks = new ArrayList<PageLink>();
		pageLinks.add(plink);
		ret.setPageLinks(pageLinks);
		
	}
	
	private static class SlotParser {

		boolean isEof = false;

		boolean isSlotFound = false;

		private String html;

		BufferedReader br;

		String buffer = null;

		ExchangeBoxSlot slot = null;

		boolean done = false;

		boolean dontReadLine = false;

		List<ExchangeBoxSlot> result;

		Matcher mCardInfo;

		Pattern pCardInfo;
		
		Pattern pSellCard;

		public SlotParser(String html) {
			this.html = html;
			result = new ArrayList<ExchangeBoxSlot>();
		}

		public List<ExchangeBoxSlot> parse() {

			initialize();

			br = new BufferedReader(new StringReader(html));

			while (!done) {

				readLine();

				// stop the loop if end-of-file is reached.
				if (isEof()) {
					if (isParsingSlot()) {
						saveCardInfo();
					}
					break;
				}

				// we need to wait for the start of slot information
				// if we are not in the mid of parsing a slot
				if (isCardInfo()) {

					if (isParsingSlot()) {
						saveCardInfo();
					}

					parseCardInfo();
					continue;
				} else if (!isParsingSlot()) {
					// skip the rest of checking until we are parsing a line.
					continue;
				}

				// we are in the mid of parsing a slot.
				parseSlot();

			}

			return result;

		}

		protected void parseSlot() {

			while (true) {

				readLine();

				if (isEof()) {
					break;
				}

				if (isCardInfo()) {
					dontReadLine = true;
					break;
				}

				parseLinks();

				// parseMoveToSafeBox();

			}

		}

		protected void parseCardInfo() {

			slot = new ExchangeBoxSlot();

			String cardThemeName = StringUtils.trim(mCardInfo.group(1));
			String cardName = StringUtils.trim(mCardInfo.group(2));
			String sCardPrice = StringUtils.trim(mCardInfo.group(3));
			double cardPrice = 0;
			try {
				cardPrice = Double.parseDouble(sCardPrice);
			} catch (NumberFormatException e) {
				// FIXME 2014-03-21 cyril better handling.
				throw e;
			}

			slot.setCardThemeName(cardThemeName);
			slot.setCardName(cardName);
			slot.setCardPrice(cardPrice);

		}
		
		protected void parseLinks() {
			
			Matcher m = pSellCard.matcher(buffer);
			
			if (!m.find()) {
				return;
			}
			
			String href = StringEscapeUtils.unescapeHtml4(StringUtils.trim(m.group(1)));
			String linkText = StringEscapeUtils.unescapeHtml4(StringUtils.trim(m.group(2)));
			
			// extract any card ID and slot ID
			extractCardIdAndSlotIdFromUrl(href);
			
			if ("出售".equals(linkText)) {
				slot.setSellUrl(href);
			} else if ("放入保险箱".equals(linkText)) {
				slot.setPutToSafeBoxUrl(href);
			} else if ("放入换卡箱".equals(linkText)) {
				slot.setPutToExchangeBoxUrl(href);
			}
			
		}
		
		protected void extractCardIdAndSlotIdFromUrl(String url) {

			try {
				List<NameValuePair> nvps = URLEncodedUtils.parse(new URI(url), "UTF-8");
				for (NameValuePair nvp : nvps) {
					if ("card".equals(nvp.getName())) {
						// card ID
						try {
							int cardId = Integer.parseInt(nvp.getValue());
							slot.setCardId(cardId);
						} catch (NumberFormatException e) {
						}
					} else if ("slot".equals(nvp.getName())) {
						// slot ID
						try {
							int slotId = Integer.parseInt(nvp.getValue());
							slot.setSlotId(slotId);
						} catch (NumberFormatException e) {
						}
					}
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
		}
		
		protected void saveCardInfo() {
			if (slot == null) {
				throw new IllegalStateException("no slot found");
			}

			validateSlot();

			result.add(slot);
		}

		protected void validateSlot() {
			// FIXME implements me.
		}

		protected void initialize() {
			
			if (pCardInfo == null) {
				pCardInfo = ParseUtil.getCardInfoPattern();
			}
			
			if (pSellCard == null) {
				// example HTML:
				// <a href="http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_sell?sid=AVMIxjV6_RFGeQ58M3VuPbVD&amp;card=4850&amp;slot=8">出售</a>
				pSellCard = Pattern.compile("<a\\s+href=\"(.+?)\"\\s*>\\s*(出售|放入保险箱|放入换卡箱)\\s*</a>", Pattern.DOTALL); 
			}
			
		}

		protected boolean isEof() {
			return buffer == null;
		}

		protected boolean isParsingSlot() {
			return slot != null;
		}

		protected boolean isCardInfo() {

			mCardInfo = pCardInfo.matcher(buffer);
			return mCardInfo.find();

		}

		protected String readLine() {
			try {
				// if 'dontReadLine' is turned on, we don't read it.
				if (dontReadLine) {
					dontReadLine = false;
					return buffer;
				}

				buffer = br.readLine();
				return buffer;

			} catch (IOException e) {
				// XXX 2014-03-21 cyril: handles this.
				e.printStackTrace();
			}
			return null;
		}

	}

}
