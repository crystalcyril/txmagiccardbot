/**
 * 
 */
package com.cppoon.tencent.magiccard.api;

import java.io.InputStream;

/**
 * 
 * @author Cyril
 * 
 */
public interface CardInfoParser {

	/**
	 * 
	 * @param listener
	 */
	void setListener(CardInfoParserListener listener);

	void parse(InputStream is);

}
