/**
 * 
 */
package com.cppoon.tencent.magiccard;

import java.io.InputStream;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface CardInfoSynchronizer {

	void synchronize(InputStream is);
	
}
