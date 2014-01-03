/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import sun.org.mozilla.javascript.internal.NativeArray;

import com.cppoon.tencent.magiccard.api.ThemeComposeListParser;
import com.cppoon.tencent.magiccard.api.ThemeComposeListParserListener;
import com.cppoon.tencent.magiccard.api.ThemeComposeRule;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SimpleThemeComposeListParser implements ThemeComposeListParser {

	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("JavaScript");

	ThemeComposeListParserListener listener;

	/**
	 * 
	 */
	public SimpleThemeComposeListParser() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.api.ThemeComposeListParser#setListener(com
	 * .cppoon.tencent.magiccard.api.ThemeComposeListParserListener)
	 */
	@Override
	public void setListener(ThemeComposeListParserListener listener) {
		this.listener = listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.api.ThemeComposeListParser#parse(java.io
	 * .InputStream)
	 */
	@Override
	public void parse(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		String s = null;
		try {

			while ((s = reader.readLine()) != null) {

				if (s.contains("var theme_compose_list")) {

					handleRecords(reader);

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @throws IOException 
	 * 
	 */
	protected void handleRecords(BufferedReader reader) throws IOException {

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

			ThemeComposeRule rule = parseThemeComposeRule(s);

			// an record is parsed, notify the listener if any.
			if (rule != null) {
				fireThemeComposeRuleParsed(rule);
			}

		}

	}

	private void fireThemeComposeRuleParsed(ThemeComposeRule rule) {

		if (listener == null)
			return;

		try {
			listener.themeComposeRuleParsed(rule);
		} catch (Throwable t) {
		}

	}

	protected ThemeComposeRuleVo parseThemeComposeRule(String s) {

		try {

			String src = "var c = " + s + ";";
			engine.eval(src);
			Object o = engine.get("c");

			NativeArray arr = (NativeArray) o;

			ThemeComposeRuleVo r = new ThemeComposeRuleVo();

			// column 1: theme ID
			int themeId = (int) Math.round((Double) arr.get(0, null));
			r.setThemeId(themeId);

			// column 2: state
			int state = (int) Math.round((Double) arr.get(1, null));
			r.setState(state);

			// column 3: target card ID
			int targetCardId = (int) Math.round((Double) arr.get(2, null));
			r.setTargetCardId(targetCardId);

			// column 4-6: required card IDs.
			int[] cardIds = new int[3];
			for (int i = 0; i <= 2; i++) {
				int cardId = (int) Math.round((Double) arr.get(3 + i, null));
				cardIds[i] = cardId;
			}
			r.setChildrenCardIds(cardIds);

			// column 7: build time
			int buildTime = (int) Math.round((Double) arr.get(6, null));
			r.setBuildTime(buildTime);

			return r;

		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
