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

}
