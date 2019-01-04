package com.yichu.server.protocol;

import org.msgpack.annotation.Message;

@Message
public class MessageObject {

	//消息格式  参考  IM聊天.txt
	private String cmd; //指令类型 例如：LOGIN\LOGOUT\CHAT\SYSTEM
	private long time; //消息发送的时间戳
	private String nickName; //消息发送人
	private String content; //消息体
	private int online;//在线人数
	
	public MessageObject() {
		super();
	}

	public MessageObject(String cmd, long time, String nickName) {
		super();
		this.cmd = cmd;
		this.time = time;
		this.nickName = nickName;
	}

	public MessageObject(String cmd, long time, String nickName, String content) {
		super();
		this.cmd = cmd;
		this.time = time;
		this.nickName = nickName;
		this.content = content;
	}

	public MessageObject(String cmd, long time, String nickName, String content, int online) {
		super();
		this.cmd = cmd;
		this.time = time;
		this.nickName = nickName;
		this.content = content;
		this.online = online;
	}

	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}
}
