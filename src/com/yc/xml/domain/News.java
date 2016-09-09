package com.yc.xml.domain;

/**
 * @ClassName:     News.java
 * @Description:   
 * @author          POM
 * @version         V1.0  
 * @Date           2016年9月9日 下午3:39:41 
 */

public class News {
	private String cTime;
	private String title;
	private String description;
	private String picUrl;
	private String url;

	public String getcTime() {
		return cTime;
	}
	public void setcTime(String cTime) {
		this.cTime = cTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}




}
