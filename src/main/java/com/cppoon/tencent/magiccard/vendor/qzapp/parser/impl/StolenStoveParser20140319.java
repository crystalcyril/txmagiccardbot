/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
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
	
	/**
	 * Regular expression for identifying the first line of stove slot.
	 */
	Pattern pNewSlotLine;
	
	/**
	 * Regular expression for extracting time format in HH:mm:ss.
	 */
	Pattern pTimeFormat;
	
	public List<StolenStove> parse(String html) {
		
		ArrayList<StolenStove> ret = new ArrayList<StolenStove>();
		
		BufferedReader br = new BufferedReader(new StringReader(html));
		
		String s;
		StolenStove si = null;
		boolean isCardInfo, isNewSlot, skipToNextSlot;
		
		try {
			
			String cardThemeName, cardName, sCardPrice;
			
			skipToNextSlot = false;
			
			while (true) {
				
				s = br.readLine();
				
				// eof of file reached.
				if (s == null) {
					if (si != null) {
						ret.add(si);
						si = null;
						break;
					}
				}
				
				Matcher mCardInfo = getCardInfoPattern().matcher(s);
				Matcher mNewSlotLine = getNewSlotLinePattern().matcher(s);
				
				isCardInfo = false;
				isNewSlot = false;
				
				if (mCardInfo.find()) {
					isCardInfo = true;
					isNewSlot = true;
				} else if (mNewSlotLine.find()) {
					isNewSlot = true;
				}
				
				
				if (skipToNextSlot) {
					if (!isCardInfo) continue;
					
					// reset status.
					skipToNextSlot = false;
				}
				
				
				if (isNewSlot) {
					// save any previous extracted stove
					if (si != null) {
						ret.add(si);
						si = null;
					}
					
					cardThemeName = null;
					cardName = null;
					sCardPrice = null;
				}
				
				if (isNewSlot) {
					
					// we need to skip until end of line or next slot.
					if (isUnopenedSlot(s)) {
						skipToNextSlot = true;
						continue;
					}
					
					si = new StolenStove();
				}
				
				// if it contains card information, parse it.
				if (isCardInfo) {
					
					si = new StolenStove();

					cardThemeName = StringUtils.trim(mCardInfo.group(1));
					cardName = StringUtils.trim(mCardInfo.group(2));
					sCardPrice = StringUtils.trim(mCardInfo.group(3));
					
					si.setCardThemeName(cardThemeName);
					si.setCardName(cardName);
					try {
						si.setCardPrice(Double.parseDouble(sCardPrice));
					} catch (NumberFormatException e) {
						log.warn("error parsing card price", e);
						return null;
					}
					
					continue;
				}
				
				//
				// handle stove status parsing
				//
				StoveStatus stoveStatus = parseStoveStatus(s);
				if (stoveStatus != null) {
					si.setStatus(stoveStatus);
					
					// for "synthesizing" line, it contains a line telling 
					// the time required to complete the synthesis.
					if (stoveStatus == StoveStatus.SYNTHESIZING) {
						Long timeRemaining = parseTimeRemaining(s);
						if (timeRemaining == null) {
							return null;
						}
						si.setSynthesisRemainingTime(timeRemaining);
					}
					
				}
				
				
			}
			
		} catch (IOException e) {
			// impossible to reach there.
			log.error("an impossible error has occurred when reading stove html", e);
			return null;
		}
		
		
		return ret;
		
	}
	
	protected boolean isUnopenedSlot(String html) {
		return (html.contains("红钻特权") || html.contains("开通红钻")); 
	}
	
	protected StoveStatus parseStoveStatus(String html) {
		
		if (html.contains("已合成")) {
			return StoveStatus.SYNTHESIZED;
		} else if (html.contains("空闲中")) {
			return StoveStatus.IDLE;
		} else if (html.contains("合成中")) {
			return StoveStatus.SYNTHESIZING;
		}
		
		return null;
		
	}
	
	protected Long parseTimeRemaining(String html) {
		
		Matcher m = getTimeFormatPattern().matcher(html);
		
		if (!m.find()) {
			return null;		// not found.
		}

		long multiplier = 60 * 60;
		long ret = 0;
		
		for (int i = 1; i <= 3; i++) {
			
			String sValue = StringUtils.trim(m.group(i));
			try {
				long value = Long.parseLong(sValue);
				ret += value * multiplier;
				multiplier /= 60;
			} catch (NumberFormatException e) {
				log.warn("error parsing stolen stove remaining time component", e);
			}
			
		}
		
		return ret;
	}
	

	protected Pattern getStovePattern() {
		if (pStoves == null) {
			
			// 1. 空闲中
            // <a href="http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_stove_steal?sid=AZSJbIiayt5edHh3EYpub1iJ">偷炉</a>
            //<br />
			pStoves = Pattern.compile("<br\\s*/?>\\s*\\d+\\s*\\.\\s*(.+?)", Pattern.DOTALL);
		}
		return pStoves;
	}
	
	protected Pattern getCardInfoPattern() {
		if (pCardInfo == null) {
			// 2. 瓢虫物语-茄二十八星瓢虫[40]
			pCardInfo = Pattern.compile("\\s*?\\d+\\s*\\.\\s*(.+?)-(.+?)\\s*\\[\\s*(\\d+)\\s*\\]", Pattern.DOTALL);
		}
		return pCardInfo;
	}
	
	protected Pattern getNewSlotLinePattern() {
		if (pNewSlotLine == null) {
			// Example patterns.
			// 2. 未偷炉(红钻特权)
			pNewSlotLine = Pattern.compile("\\s*?\\d+\\s*\\.\\s*(.+?)", Pattern.DOTALL);
		}
		return pNewSlotLine;
	}
	
	protected Pattern getTimeFormatPattern() {
		if (pTimeFormat == null) {
			pTimeFormat = Pattern.compile("(\\d+)\\s*:\\s*(\\d+)\\s*:\\s*(\\d+)");
		}
		return pTimeFormat;
	}
	
}
