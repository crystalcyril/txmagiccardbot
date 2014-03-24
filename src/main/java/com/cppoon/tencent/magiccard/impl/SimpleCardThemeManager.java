/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.impl.internal.SimpleCardThemeManagerBuilderImpl;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SimpleCardThemeManager implements CardThemeManager {

	@Override
	public Builder createBuilder() {
		return new SimpleCardThemeManagerBuilderImpl();
	}

}
