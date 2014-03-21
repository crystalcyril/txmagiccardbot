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
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.ExchangeBoxSlot;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ExchangeCardBoxParser20140320 {

	Pattern pSafeBoxUrl;

	public CardBoxInfo parse(String html) {

		return doParse(html);

	}

	protected CardBoxInfo doParse(String html) {

		// parse slots
		List<ExchangeBoxSlot> slots = parseSlots(html);

		CardBoxInfo ret = new CardBoxInfo(slots);

		// parse safe box URL.
		parseSafeBoxUrl(ret, html);

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
		href = StringEscapeUtils.unescapeHtml4(href);
		ret.setSafeBoxUrl(href);

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
				pSellCard = Pattern.compile("<a\\s+href=\"(.+?)\"\\s*>\\s*(出售|放入保险箱)\\s*</a>", Pattern.DOTALL); 
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
