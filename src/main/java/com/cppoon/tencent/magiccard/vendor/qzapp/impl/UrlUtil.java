/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.cppoon.tencent.magiccard.TxMagicCardException;

/**
 * Contains utility methods for building URLs around the mobile version of QQ
 * card.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public final class UrlUtil {

	private UrlUtil() {
		super();
	}
	
	private static final void assertSid(String sid) {
		if (sid == null) {
			throw new IllegalArgumentException("sid should not be null");
		}
	}
	
	/**
	 * Construct the URL for main page / account overview.
	 * 
	 * @param sid
	 * @return
	 */
	public static final String buildMainPageUrl(String sid) {
		
		assertSid(sid);
		
		try {
			String s = QzappMagicCardConstants.APP_URL_MAIN_PAGE + "?"
					+ QzappMagicCardConstants.SESSION_ID_NAME + "="
					+ URLEncoder.encode(sid, "UTF-8");
			
			return s;
		} catch (UnsupportedEncodingException e) {
			throw new TxMagicCardException("error building account overview URL", e);
		}
		
	}

	/**
	 * Utility method to construct the URL for safe box accessing.
	 * <p>
	 * 
	 * The URL is something like
	 * <code>http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=BQ1xjDbBy3bqoQUIxKw8ZFl7</code>.
	 * 
	 * @param sid
	 * @return the URL
	 */
	public static final String buildExchangeBoxUrl(String sid) {

		assertSid(sid);
		
		try {
			String s = QzappMagicCardConstants.SAFE_BOX_URL + "?"
					+ QzappMagicCardConstants.SESSION_ID_NAME + "="
					+ URLEncoder.encode(sid, "UTF-8");

			return s;

		} catch (UnsupportedEncodingException e) {
			throw new TxMagicCardException("failed to build exchange box URL", e);
		}

	}

	/**
	 * Utility method to construct the URL for safe box accessing.
	 * <p>
	 * 
	 * The URL is something like
	 * <code>http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=BQ1xjDbBy3bqoQUIxKw8ZFl7&t=1</code>
	 * or
	 * <code>http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=BQ1xjDbBy3bqoQUIxKw8ZFl7&t=1&n=2</code>.
	 * 
	 * @param sid
	 * @param page
	 *            the zero-based page number.
	 * @return the URL
	 */
	public static final String buildSafeBoxUrl(String sid, int page) {

		assertSid(sid);
		
		if (page < 0) {
			throw new IllegalArgumentException("page should be 0 or larger");
		}
		
		try {
			String s = QzappMagicCardConstants.SAFE_BOX_URL + "?"
					+ QzappMagicCardConstants.SESSION_ID_NAME + "="
					+ URLEncoder.encode(sid, "UTF-8") + "&t=1";

			if (page != 0) {
				s += "&n=" + (page + 1);
			}

			return s;

		} catch (UnsupportedEncodingException e) {
			throw new TxMagicCardException("failed to build safe box URL", e);
		}

	}

	/**
	 * Build the URL for stealing stoves.
	 * <p>
	 * 
	 * http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_refine?sid=AfCUb-dPhj5zk6Er83pq6IKJ&show=1&pageno=1&fuin=383664208&steal=1&tid=323
	 * 
	 * @param sid
	 * @param targetUin
	 * @param themeId
	 * @return
	 */
	public static String buildViewSynthsizableCardsForStoveStealing(String sid, int targetUin, int themeId) {
		
		assertSid(sid);
		
		try {
			String s = QzappMagicCardConstants.CARD_REFINE_URL + "?"
					+ QzappMagicCardConstants.SESSION_ID_NAME + "="
					+ URLEncoder.encode(sid, "UTF-8") 
					+ "&show=1"
					+ "&pageno=1"
					+ "&fuin=" + URLEncoder.encode(Integer.toString(targetUin), "UTF-8")
					+ "&steal=1"
					+ "&tid=" + URLEncoder.encode(Integer.toString(themeId), "UTF-8");

			return s;

		} catch (UnsupportedEncodingException e) {
			throw new TxMagicCardException("failed to build safe box URL", e);
		}

	}

	/**
	 * Build the URL for viewing synthesizable
	 * 
	 * @param sid
	 * @param themeId
	 * @return
	 */
	public static String buildViewSynthsizableCardsForOwnedStoves(
			String sid, int themeId) {
		
		assertSid(sid);
		
		try {
			String s = QzappMagicCardConstants.CARD_REFINE_URL + "?"
					+ QzappMagicCardConstants.SESSION_ID_NAME + "="
					+ URLEncoder.encode(sid, "UTF-8") 
					+ "&show=1"
					+ "&pageno=1"
					+ "&fuin=0"
					+ "&steal=0"
					+ "&tid=" + URLEncoder.encode(Integer.toString(themeId), "UTF-8");

			return s;

		} catch (UnsupportedEncodingException e) {
			throw new TxMagicCardException("failed to build safe box URL", e);
		}
		
	}

}
