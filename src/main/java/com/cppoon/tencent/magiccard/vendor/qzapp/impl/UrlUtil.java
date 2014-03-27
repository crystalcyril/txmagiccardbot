/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public final class UrlUtil {

	private UrlUtil() {
		super();
	}
	
	/**
	 * 
	 * 
	 * @param sid
	 * @param page the zero-based page number.
	 * @return
	 */
	public static final String buildSafeBoxUrl(String sid, int page) {
		
		try {
			String s = QzappMagicCardConstants.SAFE_BOX_URL + "?"
					+ QzappMagicCardConstants.SESSION_ID_NAME + "=" + URLEncoder.encode(sid, "UTF-8")
					+ "&t=1";
			
			if (page != 0) {
				s += "&n=" + (page + 1);
			}
			
			return s;
			
		} catch (UnsupportedEncodingException e) {
			// FIXME cyril better handling.
			throw new RuntimeException("failed to build safe box URL", e);
		}
		
	}

}
