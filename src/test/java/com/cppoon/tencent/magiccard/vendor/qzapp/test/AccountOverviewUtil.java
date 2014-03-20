/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public abstract class AccountOverviewUtil {

	static final Logger log = LoggerFactory.getLogger(AccountOverviewUtil.class); 
	
	public static final void print(AccountOverview overview) {

		StringBuffer sb = new StringBuffer();
		
		sb.append("\n");
		sb.append("Level: " + overview.getPlayerLevel() + "\n");
		sb.append("Exp  : " + overview.getCurrentLevelExperience() + "/"
				+ overview.getCurrentLevelTotalExperience() + "\n");
		sb.append("Coins: " + overview.getCoins() + "\n");
		sb.append("\n");
		
		sb.append("Cards in deck: " + overview.getCardsInDeck() + "\n");
		sb.append("Exchange box : " + overview.getCardsInCardExchangeBox() + "/" + overview.getCardExchangeBoxSize() + "\n");
		sb.append("\n");

		log.info("{}", sb);
		
	}
	
}
