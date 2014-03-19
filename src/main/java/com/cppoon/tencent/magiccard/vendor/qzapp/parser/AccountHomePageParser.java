/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser;

import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;

/**
 * 
 * 
 * @author Cyril
 *
 */
public interface AccountHomePageParser {

	/**
	 * 
	 * 
	 * @param html
	 * @return
	 */
	AccountOverview parse(String html);

}
