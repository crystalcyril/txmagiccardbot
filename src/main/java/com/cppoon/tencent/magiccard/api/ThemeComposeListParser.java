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
public interface ThemeComposeListParser {

	void setListener(ThemeComposeListParserListener listener);

	void parse(InputStream is);

}
