/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.impl.internal.SimpleCardManagerBuilder;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SimpleCardManager implements CardManager {

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardManager#createBuilder()
	 */
	@Override
	public Builder createBuilder() {
		return new SimpleCardManagerBuilder();
	}

}
