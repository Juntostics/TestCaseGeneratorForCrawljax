package com.junto.crawljax;
public class WebVelo {
	private String String1;
	private String String2;
	private String url;
	private String key;

	public WebVelo(String url, String String1, String key, String String2) {
		this.String1 = String1;
		this.String2 = String2;
		this.url = url;
		this.key = key;
	}

	public String getString1() {
		return String1;
	}

	public void setString1(String String1) {
		this.String1 = String1;
	}

	public String getString2() {
		return String2;
	}

	public void setString2(String String2) {
		this.String2 = String2;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
