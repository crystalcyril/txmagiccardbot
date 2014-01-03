/**
 * 
 */
package com.cppoon.tencent.magiccard.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Cyril
 * 
 */
public class IOUtil {

	/**
	 * 
	 */
	public IOUtil() {
	}

	public static void close(Closeable c) {
		if (c == null)
			return;
		try {
			c.close();
		} catch (IOException e) {
		}
	}

}
