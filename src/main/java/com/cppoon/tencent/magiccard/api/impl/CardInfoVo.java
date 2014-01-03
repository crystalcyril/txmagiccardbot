/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.util.Date;

import com.cppoon.tencent.magiccard.CardInfo;

/**
 * @author Cyril
 * 
 */
public class CardInfoVo implements CardInfo {

	int id;

	int themeId;

	String name;

	int price;

	int type;

	int pickRate;

	boolean enabled;

	int version;

	Date time;

	int itemNo;

	/**
	 * 
	 */
	public CardInfoVo() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.CardInfo#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getThemeId() {
		return themeId;
	}

	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPickRate() {
		return pickRate;
	}

	public void setPickRate(int pickRate) {
		this.pickRate = pickRate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	@Override
	public String toString() {
		return "id=" + id + ", themeId=" + themeId + ", name=" + name
				+ ", price=" + price + ", type=" + type + ", pickRate="
				+ pickRate + ", enabled=" + enabled + ", version=" + version
				+ ", time=" + time + ", itemNo=" + itemNo;
	}

}
