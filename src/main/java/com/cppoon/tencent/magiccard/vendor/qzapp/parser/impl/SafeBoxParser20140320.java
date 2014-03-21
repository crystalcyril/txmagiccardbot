/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardBoxInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardBoxInfo.PageLink;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SafeBoxParser20140320 extends ExchangeCardBoxParser20140320 {
	
	Pattern pCurrentPage;
	
	Pattern pPageLink;

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.ExchangeCardBoxParser20140320#parsePageLink(com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardBoxInfo)
	 */
	@Override
	protected void parsePageLink(CardBoxInfo ret, String html) {
		
		BufferedReader br = new BufferedReader(new StringReader(html));
		String s = null;
		
		ArrayList<PageLink> links = new ArrayList<PageLink>();

		while (true) {
			
			try {
				s = br.readLine();
				
				if (s == null) break;
			} catch (IOException e) {
				log.warn("impossible error when reading string", e);
				break;
			}
			
			// try to see if it is a 'current page' pattern or a 'page link'
			// pattern.
			Matcher mCurrentPage = getCurrentPagePattern().matcher(s);
			Matcher mPageLink = getPageLinkPattern().matcher(s);

			if (mCurrentPage.find()) {
				PageLinkImpl link = new PageLinkImpl();
				link.setCurrent(true);
				link.setPageNumber(Integer.parseInt(mCurrentPage.group(1)) - 1);
				links.add(link);
			} else if (mPageLink.find()) {
				PageLinkImpl link = new PageLinkImpl();
				link.setCurrent(false);
				link.setPageNumber(Integer.parseInt(mPageLink.group(2)) - 1);
				link.setUrl(StringEscapeUtils.unescapeHtml4(StringUtils.trim(mPageLink.group(1))));
				links.add(link);
			}
			
		}
		
		ret.setPageLinks(links);
		
	}
	
	protected Pattern getCurrentPagePattern() {
		if (pCurrentPage == null) {
			pCurrentPage = Pattern.compile("^\\s*\\[\\s*(\\d+)\\s*\\]\\s*$", Pattern.DOTALL);
		}
		return pCurrentPage;
	}
	
	protected Pattern getPageLinkPattern() {
		if (pPageLink == null) {
			// sample HTML.
			// <a href="http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=AVMIxjV6_RFGeQ58M3VuPbVD&amp;t=1&amp;n=2">2</a>
			pPageLink = Pattern.compile("^\\s*<a\\s+href=\"([^\\\"]+?)\"\\s*>\\s*(\\d+)\\s*</a>\\s*$", Pattern.DOTALL);
			pPageLink = Pattern.compile("^\\s*<a\\s+href=\"(.+?)\">\\s*(\\d+)\\s*</a>$", Pattern.DOTALL);
		}
		return pPageLink;
	}
	
}
