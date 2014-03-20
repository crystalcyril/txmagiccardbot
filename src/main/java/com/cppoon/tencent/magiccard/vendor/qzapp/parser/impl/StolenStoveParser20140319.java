/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cppoon.tencent.magiccard.api.StoveStatus;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StolenStove;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class StolenStoveParser20140319 {
	
	private static final Logger log = LoggerFactory.getLogger(StolenStoveParser20140319.class);
	
	Pattern pStoves;

	/**
	 * Regular expression for extracting card information.
	 */
	Pattern pCardInfo;
	
	public List<StolenStove> parse(String html) {

		ArrayList<StolenStove> ret = new ArrayList<StolenStove>();

		Matcher m = getStovePattern().matcher(html);
		while (m.find()) {
			
			String stoveHtml = m.group();
			log.trace("group: [[{}]]", stoveHtml);
			
			if (stoveHtml.contains("红钻特权") || stoveHtml.contains("开通红钻")) {
				continue;
			}
			
			StolenStove si = parseStove(stoveHtml);
			
			ret.add(si);
		}

		return ret;
		
	}
	
	protected StolenStove parseStove(String html) {
		
		StolenStove ret = new StolenStove();
		
		if (!parseStoveStatus(ret, html)) {
			return null;
		}
		
		return ret;
		
	}
	
	protected boolean parseStoveStatus(StolenStove ret, String html) {
		
		if (html.contains("已合成")) {
			
			ret.setStatus(StoveStatus.SYNTHESIZED);
			if (!parseCardInfo(ret, html)) return false;
			
			return true;
		} else if (html.contains("空闲中")) {
			ret.setStatus(StoveStatus.IDLE);
			return true;
		} else if (html.contains("合成中")) {
			ret.setStatus(StoveStatus.SYNTHESIZING);
			if (!parseCardInfo(ret, html)) return false;
			return true;
		}
		
		return false;
		
	}
	
	
	protected boolean parseCardInfo(StolenStove ret, String html) {

		Matcher m = getCardInfoPattern().matcher(html);
		
		if (!m.find()) {
			log.warn("unable to extract card info in stolen stove");
			return false;
		}
		
		String cardThemeName = StringUtils.trim(m.group(1));
		String cardName = StringUtils.trim(m.group(2));
		String sCardPrice = StringUtils.trim(m.group(3));
		
		
		ret.setCardThemeName(cardThemeName);
		ret.setCardName(cardName);
		try {
			ret.setCardPrice(Double.parseDouble(sCardPrice));
		} catch (NumberFormatException e) {
			log.warn("error parsing card price", e);
		}
		
		return true;
		
	}
	

	protected Pattern getStovePattern() {
		if (pStoves == null) {
			
			// 1. 空闲中
            // <a href="http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_stove_steal?sid=AZSJbIiayt5edHh3EYpub1iJ">偷炉</a>
            //<br />
//			pStoves = Pattern.compile("\\d+\\s*\\.\\s*(.*?)(?:<br\\s*/>\\s+\\d+\\.|<br\\s*/>\\s*<br\\s*/>)", Pattern.DOTALL);
			pStoves = Pattern.compile("\\d+\\s*\\.\\s*(.*?)<br\\s*/?>", Pattern.DOTALL);
		}
		return pStoves;
	}
	
	protected Pattern getCardInfoPattern() {
		if (pCardInfo == null) {
			// 2. 瓢虫物语-茄二十八星瓢虫[40]
            // <br />
			pCardInfo = Pattern.compile("\\s*(.+?)-(.+?)\\s*\\[\\s*(\\d+)\\s*\\]", Pattern.DOTALL);
		}
		return pCardInfo;
	}
	
}
