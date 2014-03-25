/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.cppoon.tencent.magiccard.api.CardInfoParser;
import com.cppoon.tencent.magiccard.api.CardInfoParserListener;
import com.cppoon.tencent.magiccard.api.ThemeCardListParser;
import com.cppoon.tencent.magiccard.api.ThemeCardListParserListener;
import com.cppoon.tencent.magiccard.api.ThemeComposeListParser;
import com.cppoon.tencent.magiccard.api.ThemeComposeListParserListener;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class JsV3CardInfoParser {
	
	ThemeComposeListParserListener themeComposeListener;

	CardInfoParserListener cardInfoListener;
	
	ThemeCardListParserListener themeCardListener;
	
	public void parse(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		String s = null;
		try {

			while ((s = reader.readLine()) != null) {

				if (s.contains("var card_list")) {

					handleCards(reader);

				} else if (s.contains("var theme_compose_list")) {
					
					handleCardSynthesisRules(reader);
					
				} else if (s.contains("var theme_card_list")) {
					
					handleThemes(reader);
					
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	private void handleThemes(BufferedReader reader) {
		
		ThemeCardListParser parser = getThemeCardListParser();
		parser.parse(reader);
		
	}

	private void handleCardSynthesisRules(BufferedReader reader) {

		ThemeComposeListParser parser = getThemeComposeListParser();
		parser.parse(reader);
		
	}

	private void handleCards(BufferedReader reader) {
		
		CardInfoParser parser = getCardInfoParser();
		parser.parse(reader);
		
	}

	public void setListener(
			ThemeComposeListParserListener themeComposeListener) {
		this.themeComposeListener = themeComposeListener;
	}

	public void setListener(CardInfoParserListener cardInfoListener) {
		this.cardInfoListener = cardInfoListener;
	}

	public void setListener(ThemeCardListParserListener themeCardListener) {
		this.themeCardListener = themeCardListener;
	}
	
	protected CardInfoParser getCardInfoParser() {
		
		SimpleCardInfoParser ret = new SimpleCardInfoParser();
		if (this.cardInfoListener != null) {
			ret.setListener(cardInfoListener);
		}
		return ret;
		
	}
	
	protected ThemeCardListParser getThemeCardListParser() {
		
		SimpleThemeCardListParser ret = new SimpleThemeCardListParser();
		if (this.themeCardListener != null) {
			ret.setListener(themeCardListener);
		}
		return ret;
		
	}
	
	protected ThemeComposeListParser getThemeComposeListParser() {
		
		SimpleThemeComposeListParser ret = new SimpleThemeComposeListParser();
		if (this.themeComposeListener != null) {
			ret.setListener(this.themeComposeListener);
		}
		return ret;
		
	}

}
