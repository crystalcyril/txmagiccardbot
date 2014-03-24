/**
 * 
 */
package com.cppoon.tencent.magiccard.api;

import java.io.InputStream;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface CardInfoParser {

	/**
	 * 
	 * @param listener
	 */
	void setListener(CardInfoParserListener listener);

	/**
	 * 
	 * 
	 * @param is
	 */
	void parse(InputStream is);

}
