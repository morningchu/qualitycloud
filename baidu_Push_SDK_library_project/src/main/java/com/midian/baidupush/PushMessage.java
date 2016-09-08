package com.midian.baidupush;

public class PushMessage {
	private String id;
	private String type;
	private MessageBase msg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MessageBase getMsg() {
		return msg;
	}

	public void setMsg(MessageBase msg) {
		this.msg = msg;
	}

}
