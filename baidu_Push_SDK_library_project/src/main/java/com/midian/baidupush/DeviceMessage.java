package com.midian.baidupush;

import com.midian.fastdevelop.afinal.annotation.sqlite.Id;
import com.midian.fastdevelop.afinal.annotation.sqlite.Table;

/**
 * 预约推送
 * 
 * @author chu
 * 
 */
@Table(name = "DeviceMessage")
public class DeviceMessage extends MessageBase {

	@Id(column = "id")
	private int id; // 表id
	private String time;// 预约时间
	private String title;// 标题
	private String content;// 内容
	private String user_id;
	private String device_id;
	private String isRead;// 是否已度

	public DeviceMessage() {
		super();
	}

	public DeviceMessage(String user_id, String device_id, String time,
			String title, String content, String isRead) {
		super();
		this.user_id = user_id;
		this.device_id = device_id;
		this.time = time;
		this.title = title;
		this.content = content;
		this.isRead = isRead;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
