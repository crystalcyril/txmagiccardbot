/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public interface SessionFactory {

	/**
	 * 
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	Session createSession(String username, String password);

}
