/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public abstract class ParseUtil {

	/**
	 * Extracts the time with format <strong>hh:mm:ss</strong> to number of 
	 * seconds.
	 * 
	 * @param m
	 * @return
	 */
	public static final Long parseClockToSeconds(Matcher m) {

		if (m == null) throw new IllegalArgumentException("missing matcher");
		
		if (m.groupCount() != 3)
			throw new IllegalArgumentException("matcher should have exactly 3 groups");

		long ret = 0;
		long multiplier = 3600;
		for (int i = 1; i <= 3; i++) {
			String s = m.group(i);
			try {
				long value = Long.parseLong(s);
				ret += value * multiplier;
				multiplier /= 60;
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return ret;
		
	}
	
	/**
	 * Returns a regular expression pattern for extracting stove status with
	 * synthesizing card and remaining time.
	 * <p>
	 * 
	 * The text to extract is <code>合成中 03:25:53</code>.
	 * <p>
	 * 
	 * The following {@link Matcher} groups will be returned:
	 * 
	 * <ul>
	 * <li>group 1: Hour</li>
	 * <li>group 2: Minute</li>
	 * <li>group 3: Second</li>
	 * </ul>
	 * 
	 * @return
	 */
	public static Pattern getStoveSynthesizingPattern() {
		return Pattern.compile("合成中\\s*(\\d+):(\\d+):(\\d+)");
	}
	
}
