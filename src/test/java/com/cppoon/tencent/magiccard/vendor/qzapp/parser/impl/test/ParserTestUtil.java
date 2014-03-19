/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.test;

import java.io.IOException;
import java.nio.charset.Charset;

import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ParserTestUtil {

	public static final String readResourceAsString(String path) throws IOException {

		String html = Resources.toString(Resources.getResource(path),
				Charset.forName("UTF-8"));
		return html;

	}

}
