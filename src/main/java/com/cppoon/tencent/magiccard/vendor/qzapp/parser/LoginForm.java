/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class LoginForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1913011323174682319L;

	private String formSubmitUrl;

	Map<String, String> values = new Hashtable<String, String>();

	/**
	 * @return the formSubmitUrl
	 */
	public String getFormSubmitUrl() {
		return formSubmitUrl;
	}
	
	/**
	 * @param formSubmitUrl the formSubmitUrl to set
	 */
	public void setFormSubmitUrl(String formSubmitUrl) {
		this.formSubmitUrl = formSubmitUrl;
	}



	/**
	 * @return the loginUrl
	 */
	public String getLoginUrl() {
		return values.get("login_url");
	}

	/**
	 * @return the goUrl
	 */
	public String getGoUrl() {
		return values.get("go_url");
	}

	/**
	 * @return the sidType
	 */
	public String getSidType() {
		return values.get("sidtype");
	}

	/**
	 * @return the aid
	 */
	public String getAid() {
		return values.get("aid");
	}

	/***
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value) {

		values.put(key, value);

	}

	@Override
	public String toString() {
		return "formSubmitUrl=" + formSubmitUrl + ", loginUrl=" + getLoginUrl()
				+ ", goUrl=" + getGoUrl() + ", sidType=" + getSidType()
				+ ", aid=" + getAid();
	}

}
