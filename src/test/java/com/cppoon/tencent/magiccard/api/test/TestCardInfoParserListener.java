/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import java.util.HashMap;
import java.util.Map;

import com.cppoon.tencent.magiccard.api.CardInfo;
import com.cppoon.tencent.magiccard.api.CardInfoParserListener;

/**
 * 
 * @author Cyril
 * 
 */
public class TestCardInfoParserListener implements CardInfoParserListener {
	
	Map<Integer/*card id*/, CardInfo> cardInfos = new HashMap<Integer/*card id*/, CardInfo>();

	public long getCardCount() {
		return cardInfos.size();
	}

	public CardInfo getCardById(int i) {
		return cardInfos.get(i);
	}

	@Override
	public void cardInfoParsed(CardInfo ci) {
		cardInfos.put(ci.getId(), ci);
	}

}
