/**
 * 
 */
package com.cppoon.tencent.magiccard;

import com.cppoon.tencent.magiccard.api.StoveStatus;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface Stove {
	
	void cancel();
	
	void synthesize(Card card);
	
	StoveStatus getStatus();
	
	void getCard();

}
