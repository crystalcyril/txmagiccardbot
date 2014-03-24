/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import java.util.Set;

import com.cppoon.tencent.magiccard.CardTheme;
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

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.CardThemeManager#getAllThemes()
	 */
	@Override
	public Set<CardTheme> getAllThemes() {
		// TODO Auto-generated method stub
		return null;
	}

}
