/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import java.util.HashMap;
import java.util.Map;

import com.cppoon.tencent.magiccard.api.CardTheme;
import com.cppoon.tencent.magiccard.api.ThemeCardListParserListener;

/**
 * @author Cyril
 * 
 */
public class TestThemeCardListParserListener implements
		ThemeCardListParserListener {

	Map<Integer/* theme ID */, CardTheme> themes = new HashMap<Integer, CardTheme>();

	/**
	 * 
	 */
	public TestThemeCardListParserListener() {
	}

	public long getThemeCount() {
		return themes.size();
	}

	public CardTheme getThemeById(int themeId) {
		return themes.get(themeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.api.ThemeCardListParserListener#cardThemeParsed
	 * (com.cppoon.tencent.magiccard.CardTheme)
	 */
	@Override
	public void cardThemeParsed(CardTheme ct) {

		themes.put(ct.getId(), ct);

	}

	public int getCount() {
		return themes.size();
	}

}
