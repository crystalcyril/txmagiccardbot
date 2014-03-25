/**
 * 
 */
package com.cppoon.tencent.magiccard.impl;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
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

	Map<Integer, CardTheme> themes;

	public SimpleCardThemeManager() {
		
		super();

		themes = new Hashtable<Integer, CardTheme>();

	}

	@Override
	public Builder createBuilder() {
		return new SimpleCardThemeManagerBuilderImpl();
	}

	@Override
	public Set<CardTheme> getAllThemes() {

		return new HashSet<CardTheme>(themes.values());

	}

	@Override
	public CardTheme findThemeById(int id) {

		CardTheme theme = themes.get(id);
		return theme;

	}

	@Override
	public void registerTheme(CardTheme cardTheme) {

		if (cardTheme == null) {
			throw new IllegalArgumentException("card theme cannot be null");
		}

		themes.put(cardTheme.getId(), cardTheme);

	}

}
