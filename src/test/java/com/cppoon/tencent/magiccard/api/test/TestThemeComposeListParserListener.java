/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import java.util.HashMap;
import java.util.Map;

import com.cppoon.tencent.magiccard.api.ThemeComposeListParserListener;
import com.cppoon.tencent.magiccard.api.ThemeComposeRule;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class TestThemeComposeListParserListener implements
		ThemeComposeListParserListener {

	Map<Integer/*target card ID*/, ThemeComposeRule> rules = new HashMap<Integer, ThemeComposeRule>();
	
	/**
	 * 
	 */
	public TestThemeComposeListParserListener() {
	}

	public Object getCount() {
		return rules.size();
	}

	public ThemeComposeRule getByCardId(int targetCardId) {
		return rules.get(targetCardId);
	}

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.api.ThemeComposeListParserListener#themeComposeRuleParsed(com.cppoon.tencent.magiccard.api.ThemeComposeRule)
	 */
	@Override
	public void themeComposeRuleParsed(ThemeComposeRule rule) {
		
		rules.put(rule.getTargetCardId(), rule);
		
	}
	
}
