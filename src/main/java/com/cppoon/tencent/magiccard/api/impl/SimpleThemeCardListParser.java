/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.org.mozilla.javascript.internal.NativeArray;

import com.cppoon.tencent.magiccard.api.CardTheme;
import com.cppoon.tencent.magiccard.api.ThemeCardListParser;
import com.cppoon.tencent.magiccard.api.ThemeCardListParserListener;

/**
 * Concrete implementation of <code>ThemeCardListParser</code>, which parses the
 * card theme information from the <code>card_info_v3.js</code> file, with
 * Javascirpt variable <code>theme_card_list</code>
 * 
 * The format of the Javascript file is:
 * 
 * <pre>
 * [61,'时尚中国风',3,1254048859,0,1,300,540,0x000000,'61|60|70|71','',[226,225,224,223,222,221,220,219,218,217,216,215,214,213,212,211,210,209,208,207,206,205,204,203,202,201],1,8,1309831121,1271289600,0,50],
 * </pre>
 * 
 * There is a Javascript comment in the variable declaration:
 * 
 * <pre>
 * //theme_id, theme_name,theme_Difficulty,theme_PublishTime,theme_PickRate, theme_Enable, theme_Prize,theme_Score,theme_color, gift, text,
 * // card1_id,..,cardn_id,theme_type,version,time,offtime,flash_src_tid
 * </pre>
 * 
 * Analysis of above data fragment:
 * 
 * <ol>
 * <li><strong>61</strong>: theme ID.</li>
 * <li><strong>时尚中国风</strong>: theme name.</li>
 * <li><strong>3</strong>: theme difficulty, that is, number of "stars" of the
 * theme.</li>
 * <li><strong>1254048859</strong>: theme publish time (epoch time). The
 * corresponding value in Java is
 * <code>new java.util.Date(1254048859 * 1000)</code> = GMT: Sun, 27 Sep 2009
 * 10:54:19. You can convert the time using the web site
 * http://www.epochconverter.com/</li>
 * <li><strong>0</strong>: theme pick rate. <strong>no idea of this field
 * yet</strong>.</li>
 * <li><strong>1</strong>:</li>
 * <li></li>
 * <li></li>
 * </ol>
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SimpleThemeCardListParser implements ThemeCardListParser {

	Logger log = LoggerFactory.getLogger(getClass());

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
					break;

				}

			}

		} catch (IOException e) {
			log.error("error parsing for card information", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.ThemeCardListParser#parse(java.io.
	 * BufferedReader)
	 */
	@Override
	public void parse(Reader reader) {

		try {
			if (reader instanceof BufferedReader) {
				handleThemes((BufferedReader) reader);
			} else {
				handleThemes(new BufferedReader(reader));
			}
		} catch (IOException e) {
			// FIXME should not do this
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

		if (listener == null)
			return;

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
			int cardId = (int) Math.round((Double) arr.get(0, null));
			r.setId(cardId);

			// column 2: theme name
			String name = (String) arr.get(1, null);
			r.setName(name);

			// column 3: difficulties
			int difficulty = (int) Math.round((Double) arr.get(2, null));
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

			// column 7: coins (prize)
			double prize = (Double) arr.get(6, null);
			r.setCoins(prize);

			// column 8: experience (score)
			int score = (int) Math.round((Double) arr.get(7, null));
			r.setExperience(score);

			// column 9: color
			// TODO cyril 2014-03-25 implement this
			
			// column 10: gift IDs
			r.setGiftIds(parseGiftIds(arr.get(9, null)));
			
			// column 11: text
			r.setText((String)arr.get(10, null));
			
			// column 12: card IDs
			NativeArray rawCardIds = (NativeArray) arr.get(11, null);
			if (rawCardIds.getLength() > 0) {
				int[] cardIds = new int[(int) rawCardIds.getLength()];
				for (Object id : rawCardIds.getIds()) {
					int idx = (Integer) id;
					cardIds[idx] = (int) Math.round((Double) rawCardIds.get(
							idx, null));
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

	private int[] parseGiftIds(Object raw) {
		
		if (raw instanceof String) {
			
			String s = (String)raw;
			String[] sid = s.split("\\|");
			ArrayList<Integer> ids = new ArrayList<Integer>();
			for (String id : sid) {
				ids.add(Integer.parseInt(id));
			}

			int[] r = new int[ids.size()];
			for (int i = 0; i < ids.size(); i++) {
				r[i] = ids.get(i);
			}
			return r;
			
		}
		
		return null;
	}

	protected Date parseDate(long l) {
		if (l <= 0)
			return null;

		return new Date(l * 1000);
	}

}
