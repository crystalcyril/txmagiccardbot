/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import sun.org.mozilla.javascript.internal.NativeArray;

import com.cppoon.tencent.magiccard.CardTheme;
import com.cppoon.tencent.magiccard.api.ThemeCardListParser;
import com.cppoon.tencent.magiccard.api.ThemeCardListParserListener;

/**
 * @author Cyril
 * 
 */
public class SimpleThemeCardListParser implements ThemeCardListParser {

	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("JavaScript");
	
	ThemeCardListParserListener listener;
	
	/**
	 * 
	 */
	public SimpleThemeCardListParser() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.api.ThemeCardListParser#setListener(com.
	 * cppoon.tencent.magiccard.api.ThemeCardListParserListener)
	 */
	@Override
	public void setListener(ThemeCardListParserListener listener) {
		this.listener = listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.ThemeCardListParser#parse(java.io.
	 * InputStream)
	 */
	@Override
	public void parse(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		String s = null;
		try {

			while ((s = reader.readLine()) != null) {

				if (s.contains("var theme_card_list")) {

					handleThemes(reader);

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void handleThemes(BufferedReader reader) throws IOException {

		String s = null;

		while ((s = reader.readLine()) != null) {

			// stop condition.
			if (s.trim().contains("];")) {
				return;
			}

			s = s.trim();
			if (s.endsWith("],")) {
				s = s.substring(0, s.length() - 1);
			}

			// do nothing for empty line.
			if (s.length() == 0)
				continue;

			CardTheme ct = parseThemeInfo(s);

			// an record is parsed, notify the listener if any.
			if (ct != null) {
				fireCardThemeParsed(ct);
			}

		}

	}

	protected void fireCardThemeParsed(CardTheme ct) {
		
		if (listener == null) return;
		
		listener.cardThemeParsed(ct);
		
		
	}

	protected CardTheme parseThemeInfo(String s) {

		try {

			String src = "var c = " + s + ";";
			engine.eval(src);
			Object o = engine.get("c");

			NativeArray arr = (NativeArray) o;

			CardThemeVo r = new CardThemeVo();

			// column 1: theme ID
			int cardId = (int)Math.round((Double) arr.get(0, null));
			r.setId(cardId);

			// column 2: theme name
			String name = (String) arr.get(1, null);
			r.setName(name);

			// column 3: difficulties
			int difficulty = (int)Math.round((Double) arr.get(2, null));
			r.setDifficulty(difficulty);

			// column 4: publish time
			long rawPublishTime = Math.round((Double) arr.get(3, null));
			r.setPublishTime(parseDate(rawPublishTime));

			// column 5: pick rate
			int pickRate = (int) Math.round((Double) arr.get(4, null));
			r.setPickRate(pickRate);

			// column 6: enabled
			Double enabled = (Double) arr.get(5, null);
			r.setEnabled(enabled == 0 ? false : true);

			// column 7: prize
			int prize = (int) Math.round((Double) arr.get(6, null));
			r.setPrize(prize);

			// column 8: score
			int score = (int) Math.round((Double) arr.get(7, null));
			r.setScore(score);

			// column 12: card IDs
			NativeArray rawCardIds = (NativeArray)arr.get(11, null);
			if (rawCardIds.getLength() > 0) {
				int[] cardIds = new int[(int)rawCardIds.getLength()];
				for (Object id : rawCardIds.getIds()) {
					int idx = (Integer) id;
					cardIds[idx] = (int)Math.round((Double)rawCardIds.get(idx, null));
				}
				r.setCardIds(cardIds);
			}

			// column 13: theme type
			int themeType = (int) Math.round((Double) arr.get(12, null));
			r.setType(themeType);
			
			// column 14: version
			int version = (int) Math.round((Double) arr.get(13, null));
			r.setVersion(version);

			// column 15: time
			int rawTime = (int) Math.round((Double) arr.get(14, null));
			r.setTime(parseDate(rawTime));
			
			// column 16: expire date
			int rawExpiryTime = (int) Math.round((Double) arr.get(15, null));
			r.setExpiryTime(parseDate(rawExpiryTime));
			
			return r;

		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	protected Date parseDate(long l) {
		if (l <= 0) return null;
		
		return new Date(l * 1000);
	}
	
}
