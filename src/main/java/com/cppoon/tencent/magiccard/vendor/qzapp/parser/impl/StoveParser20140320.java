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

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cppoon.tencent.magiccard.TxMagicCardException;
import com.cppoon.tencent.magiccard.api.StoveStatus;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StoveInfo;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class StoveParser20140320 {

	private static final Logger log = LoggerFactory
			.getLogger(StoveParser20140320.class);

	private enum NextAction {
		CONTINUE, SKIP_LINE, ERROR
	}

	Pattern pStove;

	Pattern pCardInfo;

	Pattern pSlotIdentifier;

	Pattern pCardInfoWithoutDigit;

	Pattern pSynthesisRemainingTime;

	Pattern pStoveSynthesizingPattern;

	public List<StoveInfo> parse(String html) {

		ArrayList<StoveInfo> ret = new ArrayList<StoveInfo>();

		BufferedReader br = new BufferedReader(new StringReader(html));
		String s = null;
		StoveInfo si = null;

		try {

			boolean isNewSlotEncountered = false;
			boolean isCardInfoFound = false;
			
			// read line by line and do parsing.
			while (true) {
				
				s = br.readLine();

				// true if a new slot is detected.
				isNewSlotEncountered = false;
				isCardInfoFound = false;
				boolean eof = (s == null);

				// ------------------------------ //
				// Start parsing
				// ------------------------------ //

				// check if the current string contains new slot mark. This
				// will force us to insert any created stove information
				// to the returning list.
				Matcher mCardInfo = null;
				Matcher mSlotIdentifer = null;

				if (!eof) {
					
					mCardInfo = getCardInfoPattern().matcher(s);
					mSlotIdentifer = getSlotIdentifer().matcher(s);
					
					if (mCardInfo.find()) {
						
						// slot with card inside is found.
						isNewSlotEncountered = true;
	
						// mark that we found a card info
						isCardInfoFound = true;
	
					} else if (mSlotIdentifer.find()) {
						// slot with card inside is found.
						isNewSlotEncountered = true;
					}
					
				}
				// end of new slot checking.

				// if we encounter a new slot, we need to save the previous
				// one to the returning list.
				if (isNewSlotEncountered || eof) {

					// save any previous stove information to the list.
					if (si != null) {
						
						// XXX do validation.
						
						ret.add(si);
					}

					// create a new stove and have a new start.
					if (isNewSlotEncountered) {
						si = new StoveInfo();
						si.setCardId(-1);
					}
				}
				
				if (eof) break;

				// if this line contains card information, we have to extract
				// it.
				if (isCardInfoFound) {
					
					if (!parseCardInfo(si, mCardInfo)) {
						return null; // error occurred.
					} else {
						// we are done with this line.
						continue;
					}
					
				} else if (isNewSlotEncountered) {

					// if new slot is encountered but does not contain card
					// information, probably it is in idle.

					if (s.contains("空炉位")) {
						si.setStatus(StoveStatus.IDLE);

						// we are done here.
						continue;
					} else {
						// there should not be unknown text.
						log.warn(
								"unknown text encountered in new slot string '{}'",
								s);
						return null;
					}

				}
				
				if (si != null) {

					// check if it is a stove status line.
					NextAction na = tryParseStoveStatus(si, s);
					if (na == NextAction.SKIP_LINE) continue;
					if (na == NextAction.ERROR) return null;
				
					// try to parse slot ID
					na = tryParseSlotID(ret, si, s);
					if (na == NextAction.SKIP_LINE) continue;
					if (na == NextAction.ERROR) return null;
					
					
				}
			}

		} catch (IOException e) {
			log.warn("impossible error reading from string", e);
			return null;
		}

		return ret;

	}

	protected NextAction tryParseStoveStatus(StoveInfo ret, String html) {

		// try to see if it is synthesizing.
		Matcher m = getStoveSynthesizingPattern().matcher(html);
		if (m.find()) {
			
			// try to parse it
			
			String sStoveStatus = StringUtils.trim(m.group(1));
			StoveStatus status = null;
			if ("待合成".equals(sStoveStatus)) {
				status = StoveStatus.PEND_FOR_SYNTHESIS;
			} else if ("合成中".equals(sStoveStatus)) {
				status = StoveStatus.SYNTHESIZING;
			} else {
				throw new TxMagicCardException("Unknown stove status value '" + sStoveStatus + "'");
			}
			
			Long remainingTime = ParseUtil.parseClockToSeconds(m, 1);
			if (remainingTime == null) return NextAction.ERROR;
			
			// save the result.
			ret.setStatus(status);
			ret.setSynthesisRemainingTime(remainingTime);
			
			return NextAction.SKIP_LINE;
		}
		
		// otherwise, try to parse it.
		if (html.contains("空炉位")) {
			ret.setStatus(StoveStatus.IDLE);
			return NextAction.SKIP_LINE;
		}
		
		if (html.contains("已合成")) {
			ret.setStatus(StoveStatus.SYNTHESIZED);
			return NextAction.SKIP_LINE;
		}
		
		return NextAction.CONTINUE;

	}
	
	protected NextAction tryParseSlotID(List<StoveInfo> parsedSlots, StoveInfo ret,
			String html) {
		
		Document doc = Jsoup.parse(html);

		if (doc == null) {
			return NextAction.CONTINUE;
		}

		Elements links = doc.select("a");
		for (Element link : links) {

			String href = link.attr("href");

			// skip empty links
			if (StringUtils.isEmpty(href))
				continue;
			
			// check what kind of link is it.
			if (href.contains("wl_card_clear_card") || (link.text() != null && "取消".equals(link.text()))) {
				ret.setCancelSynthesisUrl(href);
			}
			

			// find card IDs and slot IDs
			try {
				List<NameValuePair> nvps = URLEncodedUtils.parse(new URI(href),
						"UTF-8");

				String sSlotId = null;
				String sTargetId = null;

				// first, find out the slot id and target ID.
				for (NameValuePair nvp : nvps) {
					if ("slotid".equals(nvp.getName())) {
						sSlotId = nvp.getValue();
					} else if ("target_id".equals(nvp.getName()) || "targetid".equals(nvp.getName())) {
						sTargetId = nvp.getValue();
					}
				}
				
				// here, if slot ID is present, then target ID MUST BE the 
				// card ID.
				if (sSlotId != null) {
					// we found the slot ID
					try {
						int slotId = Integer.parseInt(sSlotId);
						ret.setSlotId(slotId);
					} catch (NumberFormatException e) {
						log.warn(
								"failed to parse slot ID from stove slot link "
										+ href, e);
						return NextAction.ERROR;
					}
				}
				
				if (sTargetId != null) {
					try {
						int targetId = Integer.parseInt(sTargetId);
						
						// if slot ID present, this will be card ID,
						// otherwise, it is slot ID
						if (sSlotId != null) {	// slot ID present
							ret.setCardId(targetId);
						} else {
							ret.setSlotId(targetId);
						}
						
					} catch (NumberFormatException e) {
						log.warn(
								"failed to parse slot/target ID from stove slot link "
										+ href, e);
						return NextAction.ERROR;
					}
				}
				
				return NextAction.SKIP_LINE;

			} catch (URISyntaxException e) {
				log.warn("error parsing links in stove slots (link: " + href
						+ ")", e);
				return NextAction.ERROR;
			}

		}


		// last hope: if stove status is known and is idle, we random assign
		// some unused slots.
		if (ret.getStatus() == StoveStatus.IDLE) {
			log.trace("stove slot is idle, finding an unused slot");

			int maxSlotId = -1;
			int thisSlotId = 0;
			for (StoveInfo parsedSlot : parsedSlots) {
				if (parsedSlot.getSlotId() > maxSlotId) {
					maxSlotId = parsedSlot.getSlotId();
				}
			}

			if (maxSlotId == -1) {
				thisSlotId = 0;
			} else {
				thisSlotId = maxSlotId + 1;
			}

			log.trace("maximum slot ID in parsed slots is {}", maxSlotId);
			ret.setSlotId(thisSlotId);
			return NextAction.SKIP_LINE;

		}

		return NextAction.CONTINUE;
		
	}


	protected boolean parseStoveStatus(StoveInfo ret, String html) {

		if (html.contains("已合成")) {
			ret.setStatus(StoveStatus.SYNTHESIZED);
			return true;
		} else if (html.contains("空炉位")) {
			ret.setStatus(StoveStatus.IDLE);
			return true;
		} else if (html.contains("合成中")) {
			ret.setStatus(StoveStatus.SYNTHESIZING);
			return true;
		}

		return true;

	}

	protected boolean parseCardInfo(StoveInfo ret, Matcher mCardInfo) {

		// extract text and save to stove information.
		String cardThemeName = StringUtils.trim(mCardInfo.group(1));
		String cardName = StringUtils.trim(mCardInfo.group(2));
		String sCardPrice = StringUtils.trim(mCardInfo.group(3));

		// save the information
		ret.setCardThemeName(cardThemeName);
		ret.setCardName(cardName);
		try {
			ret.setCardPrice(Double.parseDouble(sCardPrice));
		} catch (NumberFormatException e) {
			log.warn("error parsing card price for card " + cardName
					+ ", theme " + cardThemeName, e);
			return false;
		}

		return true;
	}

	protected Pattern getStoveSynthesizingPattern() {
		if (pStoveSynthesizingPattern == null) {
			pStoveSynthesizingPattern = ParseUtil.getStoveSynthesizingPattern();
		}
		return pStoveSynthesizingPattern;
	}

	protected boolean parseSynthesisRemainingTime(StoveInfo ret, String html) {

		Matcher m = getSynthesisRemainingTimePattern().matcher(html);

		// report failure if pattern not found.
		if (!m.find()) {
			return false;
		}

		Long v = ParseUtil.parseClockToSeconds(m);

		if (v == null) {
			return false;
		}

		ret.setSynthesisRemainingTime(v);

		return true;

	}

	protected boolean parseSlotId(List<StoveInfo> parsedSlots, StoveInfo ret,
			String html) {

		Document doc = Jsoup.parse(html);

		if (doc == null) {
			log.warn("failed to parse HTML document for slot ID in stove");
			return false;
		}

		Elements links = doc.select("a");
		for (Element link : links) {

			String href = link.attr("href");

			// skip empty links
			if (StringUtils.isEmpty(href))
				continue;

			try {
				List<NameValuePair> nvps = URLEncodedUtils.parse(new URI(href),
						"UTF-8");

				for (NameValuePair nvp : nvps) {
					if ("target_id".equals(nvp.getName())
							|| "slotid".equals(nvp.getName())) {
						// we found it!
						try {
							int slotId = Integer.parseInt(nvp.getValue());
							ret.setSlotId(slotId);
							return true;
						} catch (NumberFormatException e) {
							log.warn(
									"failed to parse slot ID from stove slot link "
											+ href, e);
							return false;
						}
					}
				}

			} catch (URISyntaxException e) {
				log.warn("error parsing links in stove slots (link: " + href
						+ ")", e);
				return false;
			}

		}

		log.trace("failed to extract slot ID");

		// last hope: if stove status is known and is idle, we random assign
		// some unused slots.
		if (ret.getStatus() == StoveStatus.IDLE) {
			log.trace("stove slot is idle, finding an unused slot");

			int maxSlotId = -1;
			int thisSlotId = 0;
			for (StoveInfo parsedSlot : parsedSlots) {
				if (parsedSlot.getSlotId() > maxSlotId) {
					maxSlotId = parsedSlot.getSlotId();
				}
			}

			if (maxSlotId == -1) {
				thisSlotId = 0;
			} else {
				thisSlotId = maxSlotId + 1;
			}

			log.trace("maximum slot ID in parsed slots is {}", maxSlotId);
			ret.setSlotId(thisSlotId);
			return true;

		}

		return false;

	}

	protected Pattern getStovePattern() {
		if (pStove == null) {

			// sample HTML.
			//
			// <br />
			// 5. 空炉位
			// <a
			// href="http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_strategy?sid=AVMIxjV6_RFGeQ58M3VuPbVD">炼卡</a>
			// <br />

			// failed
			pStove = Pattern
					.compile(
							"<br\\s*/?>\\s*(\\d+).*?\\.\\s*(.*?)<br\\s*/?>\\s*(.*?)\\s*.+?<br\\s*/?>",
							Pattern.DOTALL);

			pStove = Pattern
					.compile(
							"<br\\s*/?>\\s*(\\d+).*?\\.\\s*(.*?)(<br\\s*/?>|<a\\s+.*?</a>)\\s*(.*?)\\s*.+?<br\\s*/?>",
							Pattern.DOTALL);
		}
		return pStove;
	}

	protected Pattern getCardInfoPattern() {
		if (pCardInfo == null) {
			// 2. 瓢虫物语-茄二十八星瓢虫[40]
			pCardInfo = Pattern
					.compile(
							"\\s*\\d+\\s*\\.\\s*(.+)\\s*-\\s*(.+)\\s*\\[\\s*(\\d+)\\s*]",
							Pattern.DOTALL);
		}
		return pCardInfo;
	}

	protected Pattern getSlotIdentifer() {
		if (pSlotIdentifier == null) {
			// example text to match:
			// - 4. 空炉位
			pSlotIdentifier = Pattern.compile("\\s*\\d+\\s*\\.\\s*(.+)\\s*",
					Pattern.DOTALL);
		}
		return pSlotIdentifier;
	}

	protected Pattern getCardInfoPatternWithoutLeadingDigit() {
		if (pCardInfoWithoutDigit == null) {
			// 2. 瓢虫物语-茄二十八星瓢虫[40]
			// <br />
			pCardInfoWithoutDigit = Pattern.compile(
					"\\s*(.+?)-(.+?)\\s*\\[\\s*(\\d+)\\s*\\]", Pattern.DOTALL);
		}
		return pCardInfoWithoutDigit;
	}

	protected Pattern getIsSynthesizedPattern() {
		if (pCardInfoWithoutDigit == null) {
			// 2. 瓢虫物语-茄二十八星瓢虫[40]
			// <br />
			pCardInfoWithoutDigit = Pattern.compile(
					"\\s*(.+?)-(.+?)\\s*\\[\\s*(\\d+)\\s*\\]", Pattern.DOTALL);
		}
		return pCardInfoWithoutDigit;
	}

	protected Pattern getSynthesisRemainingTimePattern() {
		if (pSynthesisRemainingTime == null) {
			pSynthesisRemainingTime = Pattern
					.compile("(\\d+)\\s*:\\s*(\\d+)\\s*:\\s*(\\d+)");
		}
		return pSynthesisRemainingTime;
	}

}
