/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import sun.org.mozilla.javascript.internal.NativeArray;

import com.cppoon.tencent.magiccard.api.CardInfo;
import com.cppoon.tencent.magiccard.api.CardInfoParser;
import com.cppoon.tencent.magiccard.api.CardInfoParserListener;

/**
 * Simple implementation of the <code>CardInfoParser</code>.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SimpleCardInfoParser implements CardInfoParser {

	CardInfoParserListener listener;

	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("JavaScript");

	public void setListener(CardInfoParserListener listener) {
		this.listener = listener;
	}

	@Override
	public void parse(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		String s = null;
		try {

			while ((s = reader.readLine()) != null) {

				if (s.contains("var card_list")) {

					handleCards(reader);
					break;

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.api.CardInfoParser#parse(java.io.Reader)
	 */
	@Override
	public void parse(Reader reader) {

		try {
			if (reader instanceof BufferedReader) {
				handleCards((BufferedReader) reader);
			} else {
				handleCards(new BufferedReader(reader));
			}
		} catch (IOException e) {
			// FIXME should not do this.
			e.printStackTrace();
		}

	}

	protected void handleCards(BufferedReader reader) throws IOException {

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

			CardInfo ci = parseCardInfo(s);

			// an record is parsed, notify the listener if any.
			if (ci != null) {
				fireCardInfoParsed(ci);
			}

		}

	}

	protected void fireCardInfoParsed(CardInfo ci) {

		if (listener == null)
			return;

		try {
			listener.cardInfoParsed(ci);
		} catch (Throwable t) {
			// mute all error
		}

	}

	protected CardInfo parseCardInfo(String s) {

		try {

			String src = "var c = " + s + ";";
			engine.eval(src);
			Object o = engine.get("c");

			NativeArray arr = (NativeArray) o;

			CardInfoVo r = new CardInfoVo();

			// column 1: parse card ID
			Double cardId = (Double) arr.get(0, null);
			r.setId((int) Math.round(cardId));

			// column 2: theme ID
			Double themeId = (Double) arr.get(1, null);
			r.setThemeId((int) Math.round(themeId));

			// column 3: card name
			String name = (String) arr.get(2, null);
			r.setName(name);

			// column 4: price
			Double price = (Double) arr.get(3, null);
			r.setPrice((int) Math.round(price));

			// column 5: Type
			Double type = (Double) arr.get(4, null);
			r.setType((int) Math.round(type));

			// column 6: pick rate
			Double pickRate = (Double) arr.get(5, null);
			r.setPickRate((int) Math.round(pickRate));

			// column 7: enabled
			Double enabled = (Double) arr.get(6, null);
			r.setEnabled(enabled == 0 ? false : true);

			// column 8: version
			Double version = (Double) arr.get(7, null);
			r.setVersion((int) Math.round(version));

			// column 9: time
			Double time = (Double) arr.get(8, null);
			if (time != 0) {
				Date d = null;
				Calendar c = Calendar.getInstance();
				d = new Date(Math.round(time) * 1000);
				c.setTime(d);
				c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				r.setTime(c.getTime());
			}

			// column 10: item no.
			Double itemNo = (Double) arr.get(9, null);
			r.setItemNo((int) Math.round(itemNo));

			return r;

		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
