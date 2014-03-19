/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface LoginPageParser {

	/**
	 * 
	 * 
	 * @param html
	 * @return
	 */
	LoginForm parse(String html);
	
}
