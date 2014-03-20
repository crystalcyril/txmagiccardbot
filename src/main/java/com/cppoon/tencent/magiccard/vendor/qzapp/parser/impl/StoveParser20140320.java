/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl;

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

import com.cppoon.tencent.magiccard.api.StoveStatus;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StoveInfo;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class StoveParser20140320 {
	
	private static final Logger log = LoggerFactory.getLogger(StoveParser20140320.class);

	Pattern pStove;
	
	Pattern pCardInfo;
	
	Pattern pSynthesisRemainingTime;
	
	public List<StoveInfo> parse(String html) {
		
		Matcher m = getStovePattern().matcher(html);
		
		ArrayList<StoveInfo> ret = new ArrayList<StoveInfo>();
		
		while (m.find()) {
			
			log.trace("parsed stove section: [[{}]]", m.group());
			
			StoveInfo si = new StoveInfo();
			
			Matcher mCardInfo = getCardInfoPattern().matcher(m.group(2));
			if (mCardInfo.find()) {
				
				// the card is being refined.
				
				// extract text and save to stove information.
				String cardThemeName = StringUtils.trim(mCardInfo.group(1));
				String cardName = StringUtils.trim(mCardInfo.group(2));
				String sCardPrice = StringUtils.trim(mCardInfo.group(3));
				
				si.setCardThemeName(cardThemeName);
				si.setCardName(cardName);
				try {
					si.setCardPrice(Double.parseDouble(sCardPrice));
				} catch (NumberFormatException e) {
					log.warn("error parsing card price for card " + cardName + ", theme " + cardThemeName, e); 
					return null;
				}
				
			}

			// parse stove status.
			if (!parseStoveStatus(si, m.group(0))) {
				return null;
			}
			
			if (si.getStatus() == StoveStatus.SYNTHESIZING) {
				if (!parseSynthesisRemainingTime(si, m.group(0))) {
					return null;
				}
			}
			
			// parse slot ID
			if (!parseSlotId(ret, si, m.group(0))) {
				return null;
			}
			
			
			// parse slot I
			ret.add(si);
			
		}
		
		return ret;
		
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
	

	protected boolean parseSlotId(List<StoveInfo> parsedSlots, StoveInfo ret, String html) {
		
		Document doc = Jsoup.parse(html);
		
		if (doc == null) {
			log.warn("failed to parse HTML document for slot ID in stove");
			return false;
		}
		
		Elements links = doc.select("a");
		for (Element link : links) {
			
			String href = link.attr("href");
			
			// skip empty links
			if (StringUtils.isEmpty(href)) continue;
			
			try {
				List<NameValuePair> nvps = URLEncodedUtils.parse(new URI(href), "UTF-8");
				
				for (NameValuePair nvp : nvps) {
					if ("target_id".equals(nvp.getName()) || "slotid".equals(nvp.getName())) {
						// we found it!
						try {
							int slotId = Integer.parseInt(nvp.getValue());
							ret.setSlotId(slotId);
							return true;
						} catch (NumberFormatException e) {
							log.warn("failed to parse slot ID from stove slot link " + href, e);
							return false;
						}
					}
				}
				
			} catch (URISyntaxException e) {
				log.warn("error parsing links in stove slots (link: " + href + ")", e);
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
			// <a href="http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_strategy?sid=AVMIxjV6_RFGeQ58M3VuPbVD">炼卡</a>
			// <br />
			
			pStove = Pattern.compile("<br\\s*/?>\\s*(\\d+).*?\\.\\s*(.*?)<br\\s*/?>\\s*(.*?)\\s*.+?<br\\s*/?>", Pattern.DOTALL);
		}
		return pStove;
	}
	
	protected Pattern getCardInfoPattern() {
		if (pCardInfo == null) {
			// 2. 瓢虫物语-茄二十八星瓢虫[40]
            // <br />
			pCardInfo = Pattern.compile("\\s*(.+?)-(.+?)\\s*\\[\\s*(\\d+)\\s*\\]", Pattern.DOTALL);
		}
		return pCardInfo;
	}
	
	protected Pattern getIsSynthesizedPattern() {
		if (pCardInfo == null) {
			// 2. 瓢虫物语-茄二十八星瓢虫[40]
            // <br />
			pCardInfo = Pattern.compile("\\s*(.+?)-(.+?)\\s*\\[\\s*(\\d+)\\s*\\]", Pattern.DOTALL);
		}
		return pCardInfo;
	}
	
	protected Pattern getSynthesisRemainingTimePattern() {
		if (pSynthesisRemainingTime == null) {
			pSynthesisRemainingTime = Pattern.compile("(\\d+)\\s*:\\s*(\\d+)\\s*:\\s*(\\d+)");
		}
		return pSynthesisRemainingTime;
	}
	
}
