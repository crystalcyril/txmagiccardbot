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
public interface Session {

	AccountOverview getAccountOverview();

	SessionAuthStatus getAuthStatus();

}
