/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.impl;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public abstract class QzappMagicCardConstants {

	/**
	 * Domain for the mobile application.
	 * <p>
	 * 
	 * mfkp = 魔法卡片
	 */
	public static final String APP_DOMAIN = "mfkp.qzapp.z.qq.com";

	/**
	 * The base URL for the application. This URL is <strong>not </strong> ended
	 * with slash character (/).
	 */
	public static final String APP_BASE_URL = "http://" + APP_DOMAIN
			+ "/qshow/cgi-bin";

	/**
	 * The name of the session ID of the remote web site.
	 */
	public static final String SESSION_ID_NAME = "sid";

	/**
	 * The URL of the application's main page.
	 */
	public static final String APP_URL_MAIN_PAGE = APP_BASE_URL + "/wl_card_mainpage";
	
}
