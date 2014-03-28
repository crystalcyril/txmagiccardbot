/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser;

import java.io.InputStream;
import java.util.List;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface CardRefineParser {

	List<SynthesizeCardInfo> parse(InputStream is);

}
