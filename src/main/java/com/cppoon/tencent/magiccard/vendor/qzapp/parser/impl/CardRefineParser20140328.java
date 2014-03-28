/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.cppoon.tencent.magiccard.TxMagicCardException;
import com.cppoon.tencent.magiccard.util.IOUtil;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardRefineParser;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.SynthesizeCardInfo;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class CardRefineParser20140328 implements CardRefineParser {
	
	private static class CardsParser {
		
		ArrayList<SynthesizeCardInfo> ret;

		/**
		 * Pattern for extracting card info.
		 * <p>
		 * 
		 * Captured groups:
		 *
		 * 1. card name
		 * <br/>
		 * 2. card price
		 */
		Pattern pCard;
		
		// save the last match result of card.
		Matcher mCard;
		
		Matcher mCardsCollected;

		/**
		 * Pattern for extracting card collected.
		 */
		Pattern pCardsCollected;
		
		Matcher mSynthesizeUrl;
		
		Pattern pSynthesizeUrl;
		
		/**
		 * Save the read string from the external source.
		 */
		String buffer;
		
		SynthesizeCardInfo parsed;
		

		public List<SynthesizeCardInfo> parse(BufferedReader br) {
			
			initialize();
			
			ret = new ArrayList<SynthesizeCardInfo>();
			
			
			while (true) {
			
				// read a line.
				try {
					buffer = br.readLine();
				} catch (IOException e) {
					throw new TxMagicCardException("impossible error reading string", e);
				}
				
				// end of string
				if (buffer == null) {
					saveParsedCard();
					break;
				}
				
				// if it is the start of a card. save the old one if exists, 
				// and start a new one
				if (isCard()) {
					
					saveParsedCard();
					
					initNewCard();
					
					parseNewCard();
					
					// we are done.
					continue;
					
				}
				
				tryParseCardsCollected();
				
				tryParseSynthesizeUrl();
				
			}
			
			return ret;
			
		}

		private void tryParseSynthesizeUrl() {
			
			// create or reuse matcher.
			if (mSynthesizeUrl == null) {
				mSynthesizeUrl = pSynthesizeUrl.matcher(buffer);
			} else {
				mSynthesizeUrl.reset(buffer);
			}
			
			// return if not found
			if (!mSynthesizeUrl.find()) return;
			
			String url = trimHtml(mSynthesizeUrl.group(1));
			
			// the URL must contains valid path components.
			if (!url.contains("/wl_card_refine")) {
				return;
			}
			
			// save the synthesis URL.
			parsed.setSynthesizeUrl(url);
			
			// parse arguments in the link
			
			try {
				List<NameValuePair> nvps = URLEncodedUtils.parse(new URI(url), "UTF-8");
				
				// look for the following attributes:
				// - card ID
				// - card theme ID
				// - 
				for (NameValuePair nvp : nvps) {
					
					if ("tid".equals(nvp.getName())) {
						
						// card theme ID
						parsed.setCardThemeId(Integer.parseInt(nvp.getValue()));
						
					} else if ("id".equals(nvp.getName())) {
						
						// card ID
						parsed.setCardId(Integer.parseInt(nvp.getValue()));
						
					}
					
				}
				
			} catch (URISyntaxException e) {
				throw new TxMagicCardException("unable to parse card synthesis url for parameterss", e);
			}
			
		}

		private void tryParseCardsCollected() {

			// create or reuse matcher.
			if (mCardsCollected == null) {
				mCardsCollected = pCardsCollected.matcher(buffer);
			} else {
				mCardsCollected.reset(buffer);
			}
			
			// return if not found.
			if (!mCardsCollected.find()) return;
			
			// extract the digit
			try {
				
				 int count = Integer.parseInt(trimHtml(mCardsCollected.group(1)));
				 parsed.setCardsCollected(count);
				
			} catch (NumberFormatException e) {
				throw new TxMagicCardException("error parsing cards collected count", e);
			}
			
		}

		private void parseNewCard() {
			
			String cardName = trimHtml(mCard.group(1));
			String sCardPrice = trimHtml(mCard.group(2));
			double cardPrice = 0;
			try {
				cardPrice = Double.parseDouble(sCardPrice);
			} catch (NumberFormatException e) {
				throw new TxMagicCardException("error parsing card price", e);
			}
			
			// save to parsed object
			parsed.setCardName(cardName);
			parsed.setCardPrice(cardPrice);
			
		}
		
		protected String trimHtml(String raw) {
			return StringUtils.trim(StringEscapeUtils.unescapeHtml4(raw));
		}

		private void initNewCard() {

			parsed = new SynthesizeCardInfo();
			
		}

		/**
		 * Save any parsed object. If no object is parsed, this method will 
		 * do nothing and no error will be reported.
		 */
		protected void saveParsedCard() {
			
			// no error will be raised.
			if (parsed == null) return;

			// XXX validate this object
			
			// save this
			ret.add(parsed);
			
			// consume this.
			parsed = null;
			
		}

		protected void initialize() {
			
			// pattern for extracting start of card info
			// sample HTML
			// 3. 鼎脈蜻蜓[40]
			pCard = Pattern.compile("\\d+\\s*\\.\\s*(.+?)\\s*\\[\\s*(\\d+)\\s*\\]", Pattern.DOTALL);
			
			// pattern for extracting cards collected count.
			pCardsCollected = Pattern.compile("已有\\s*(\\d+)\\s*张");
			
			// pattern for extract synthesis URL.
			pSynthesizeUrl = Pattern.compile("<a\\s+href=\"(.*?)\"\\s*>");
			
		}
		
		protected boolean isCard() {
			
			// 3. 鼎脈蜻蜓[40]
			mCard = pCard.matcher(buffer);
			
			if (!mCard.find()) return false;
			
			return true;
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardRefineParser#parse
	 * (java.io.InputStream)
	 */
	@Override
	public List<SynthesizeCardInfo> parse(InputStream is) {

		return doParse(is);

	}

	protected List<SynthesizeCardInfo> doParse(InputStream is) {

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		CardsParser parser = new CardsParser();
		
		List<SynthesizeCardInfo> ret = parser.parse(br);
		
		IOUtil.close(br);
		
		return ret;
		
	}

}
