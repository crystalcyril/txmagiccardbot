/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl;

import java.net.URI;
import java.net.URISyntaxException;
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

import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.AccountHomePageParser;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StolenStove;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StoveInfo;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class AccountHomePageParser20140318 implements AccountHomePageParser {
	
	private static final Logger log = LoggerFactory.getLogger(AccountHomePageParser20140318.class);

	StoveParser20140320 stovesParser = new StoveParser20140320();
	
	StolenStoveParser20140319 stolenStovesParser = new StolenStoveParser20140319();
	
	/**
	 * Regular expression for extracting player level.
	 */
	private Pattern pPlayerLevel;
	
	/**
	 * Regular expression for extracting player's coins.
	 */
	private Pattern pCoins;
	
	/**
	 * Regular expression for extracting number of cards in deck.
	 */
	private Pattern pCardsInDeck;
	
	/**
	 * Regular expression for extracting card exchange box status (number of 
	 * cards and box size).
	 */
	private Pattern pCardsInCardExchangeBox;
	
	/**
	 * Regular expression for extracting stoves status area.
	 */
	private Pattern pStoveSection;
	
	/**
	 * Regular expression for extracting stolen stoves status area.
	 */
	private Pattern pStolenStoveSection;
	
	
	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.vendor.qzapp.parser.AccountHomePageParser#parse(java.lang.String)
	 */
	@Override
	public AccountOverview parse(String html) {
		
		AccountOverview ret = new AccountOverview();
		
		// parse player level and experience together as they are in the
		// same line.
		if (!parseUin(ret, html)) {
			return null;
		}
		
		// parse player level and experience together as they are in the
		// same line.
		if (!parsePlayerLevelAndExperience(ret, html)) {
			return null;
		}
		
		// parse coin
		if (!parseCoins(ret, html)) {
			return null;
		}
		
		// parse number of cards in deck.
		if (!parseCardsInDeck(ret, html)) {
			return null;
		}
		
		// parse card exchange box status.
		if (!parseCardExchangeBox(ret, html)) {
			return null;
		}
		
		// parse stoves
		if (!parseStoves(ret, html)) {
			return null;
		}
		
		// parse stolen stoves
		if (!parseStolenStoves(ret, html)) {
			return null;
		}
		
		return ret;
	}

	/**
	 * Parse for UIN.
	 * <p>
	 * 
	 * This information can be found in a link named "应用".
	 * 
	 * @param ret
	 * @param html
	 * @return
	 */
	protected boolean parseUin(AccountOverview ret, String html) {
		
		Document doc = Jsoup.parse(html);
		
		Elements elements = doc.select("a");
		for (Element e : elements) {
			
			String linkText = StringUtils.trim(e.text());
			// we are interested with link of matching text.
			if (!"应用".equals(linkText)) {
				continue;
			}
			
			String url = StringUtils.trim(e.attr("href"));
			if (StringUtils.isEmpty(url)) continue;
			
			// found the link, but we need to extract the uin. the parameter
			// is named "B_UID".
			try {
				List<NameValuePair> nvps = URLEncodedUtils.parse(new URI(url), "UTF-8");
				for (NameValuePair nvp : nvps) {
					if (!"B_UID".equals(nvp.getName())) continue;
					
					try {
						long uin = Long.parseLong(nvp.getValue());
						ret.setUin(uin);
						return true;
					} catch (NumberFormatException ex) {
						log.warn("error parsing value " + nvp.getValue() + " for uin from URL " + url + ". information maybe missed", ex);
					}
				}
				
			} catch (URISyntaxException ex) {
				log.warn("error parsing url " + url + " for uin. information maybe missed", ex);
			}
			
		}
		
		// not found.
		return false;
	}

	/**
	 * Extract player level and experience values.
	 * 
	 * @param ret
	 * @param html
	 * @return
	 */
	protected boolean parsePlayerLevelAndExperience(AccountOverview ret, String html) {
		
		Matcher m = getPlayerLevelAndExperiencePattern().matcher(html);
		
		// report failure if not found.
		if (!m.find()) {
			return false;
		}
		
		// get the captured strings.
		String sLevel = m.group(1);
		String sCurrentExp = m.group(2);
		String sLevelExp= m.group(3);
		
		log.trace("level: [[{}]], exp: [[{}]], experience required to upgrade: [[{}]]", sLevel, sCurrentExp, sLevelExp);
		
		//
		// read the following link to have an explanation of the experience 
		// number.
		//   http://kf.qq.com/faq/120322fu63YV130422BriEbM.html
		//
		// it states that the experience shown reflects the experienced gained
		// in THIS level only, not accumulated experience.
		//
		
		// parse to number
		try {
			int level = Integer.parseInt(sLevel);
			int exp = Integer.parseInt(sCurrentExp);
			int levelUpExp = Integer.parseInt(sLevelExp);
			
			ret.setPlayerLevel(level);
			ret.setCurrentLevelExperience(exp);
			ret.setCurrentLevelTotalExperience(levelUpExp);
		} catch (NumberFormatException e) {
			log.warn("an error has occurred when converting player level/experience/level up required experience", e);
			return false;
		}
		
		return true;
	}
	
	
	
	/**
	 * Extract the coins owned by the player.
	 * 
	 * @param ret
	 * @param html
	 * @return
	 */
	protected boolean parseCoins(AccountOverview ret, String html) {
		
		Matcher m = getCoinsPattern().matcher(html);
		
		if (!m.find()) {
			// pattern not found, an error.
			return false;
		}
		
		String sCoins = StringUtils.trim(m.group(1));
		
		// parse for numeric value
		try {
			double coins = Double.parseDouble(sCoins);
			ret.setCoins(coins);
		} catch (NumberFormatException e) {
			log.warn("error extracting player coins value", e);
			return false;
		}
		
		return true;
	}

	
	protected boolean parseCardsInDeck(AccountOverview ret, String html) {
		
		Matcher m = getCardsInDeckPattern().matcher(html);
		
		if (!m.find()) {
			// pattern not found, an error.
			return false;
		}
		
		String sCardsInDeck = StringUtils.trim(m.group(1));
		
		// parse for numeric value
		try {
			int cardsInDeck = Integer.parseInt(sCardsInDeck);
			ret.setCardsInDeck(cardsInDeck);
		} catch (NumberFormatException e) {
			log.warn("error extracting cards in deck", e);
			return false;
		}
		
		return true;
		
	}
	
	
	protected boolean parseCardExchangeBox(AccountOverview ret, String html) {
		
		Matcher m = getCardExchangeBoxPattern().matcher(html);
		
		if (!m.find()) {
			// pattern not found, an error.
			return false;
		}
		
		String sCardsInDeck = StringUtils.trim(m.group(1));
		String sBoxSize = StringUtils.trim(m.group(2));
		
		// parse for numeric value - cards in exchange box
		try {
			int cardsInDeck = Integer.parseInt(sCardsInDeck);
			ret.setCardsInCardExchangeBox(cardsInDeck);
		} catch (NumberFormatException e) {
			log.warn("error extracting cards in deck", e);
			return false;
		}
		
		// parse for numeric value - card exchange box size
		try {
			int v = Integer.parseInt(sBoxSize);
			ret.setCardExchangeBoxSize(v);
		} catch (NumberFormatException e) {
			log.warn("error extracting cards in deck", e);
			return false;
		}
		
		return true;
		
	}


	/**
	 * What a stove consists of:
	 * 
	 * <ul>
	 * <li>Card theme name</li>
	 * <li>Card name</li>
	 * <li>Card cost</li>
	 * <li>Compose status</li>
	 * <li>Compose time left (applicable only if card is being synthesized)</li>
	 * <li>Stove slot ID (0-based)</li>
	 * </ul>
	 * 
	 */
	protected boolean parseStoves(AccountOverview ret, String html) {
		
		Matcher m = getStoveSectionPattern().matcher(html);
		
		if (!m.find()) {
			log.warn("unable to locate stoves section");
			return false;
		}
		
		String stoveHtml = m.group(1);
		List<StoveInfo> stoves = stovesParser.parse(stoveHtml);
		if (stoves == null) return false;
		
		ret.setStoveInfos(stoves);
		return true;
	}
	
	protected boolean parseStolenStoves(AccountOverview ret, String html) {
		
		Matcher m = getStolenStoveSectionPattern().matcher(html);
		
		if (!m.find()) {
			log.warn("unable to locate stolen stoves section");
			return false;
		}
		
		String stolenStoveHtml = m.group(1);
		
		List<StolenStove> stoves = stolenStovesParser.parse(stolenStoveHtml);
		if (stoves == null) return false;
		
		ret.setStolenStoveInfos(stoves);
		
		return true;
	}

	
	protected Pattern getPlayerLevelAndExperiencePattern() {
		if (pPlayerLevel == null) {
			pPlayerLevel = Pattern.compile("<br\\s*?/?>.*?等级\\s*?:\\s*?(\\d+)\\((\\d+)\\s*?/\\s*?(\\d+)\\)", Pattern.DOTALL);
		}
		return pPlayerLevel;
	}
	
	protected Pattern getCoinsPattern() {
		if (pCoins == null) {
			pCoins = Pattern.compile("金币\\s*?:\\s*?(\\d+)", Pattern.DOTALL);
		}
		return pCoins;
	}

	protected Pattern getCardsInDeckPattern() {
		if (pCardsInDeck == null) {
			pCardsInDeck = Pattern.compile("抽卡\\s*\\(.*?(\\d+).*?\\)", Pattern.DOTALL);
		}
		return pCardsInDeck;
	}
	
	protected Pattern getCardExchangeBoxPattern() {
		if (pCardsInCardExchangeBox == null) {
			pCardsInCardExchangeBox = Pattern.compile("查看换卡箱\\s*\\([^\\d]*(\\d+)\\s*/[^\\d]*(\\d+).*?\\)", Pattern.DOTALL);
		}
		return pCardsInCardExchangeBox;
	}
	
	protected Pattern getStoveSectionPattern() {
		if (pStoveSection == null) {
			pStoveSection = Pattern.compile("【炼卡位】(.*?)【偷炉位】", Pattern.DOTALL);
		}
		return pStoveSection;
	}
	
	protected Pattern getStolenStoveSectionPattern() {
		if (pStolenStoveSection == null) {
			pStolenStoveSection = Pattern.compile("【偷炉位】(.*?)<br\\s*/>\\s*?<br\\s*/>", Pattern.DOTALL);
		}
		return pStolenStoveSection;
	}
	
}
