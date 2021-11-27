package com.networkH2021.gachon.ChatServer;

import java.io.Writer;

/*
 * 유저정보 - Writer pw
 * 귓속말 - String 닉네임
 * 방장  - int 1  or  0
 * 벙어리 - int 1 or 0 
 */
public class ChatUser {
	private Writer writer;
	private String name;
	private boolean master = false; // true면 방장
	private boolean chattingBan; // true면 말 금지

	public ChatUser() {
		super();
	}
	
	public ChatUser(Writer writer, String name, boolean master, boolean chattingBan) {
		super();
		this.writer = writer;
		this.name = name;
		this.master = master;
		this.chattingBan = chattingBan;
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public boolean isChattingBan() {
		return chattingBan;
	}

	public void setChattingBan(boolean chattingBan) {
		this.chattingBan = chattingBan;
	}

	@Override
	public String toString() {
		return "ChatUser [writer=" + writer + ", name=" + name + ", master=" + master + ", chattingBan=" + chattingBan
				+ "]";
	}

	
}
