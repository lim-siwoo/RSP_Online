package com.networkH2021.gachon.client;

import com.networkH2021.gachon.GameLauncher;
import com.networkH2021.gachon.MainLobby;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClientApp {
	
	private static final String SERVER_IP = "14.47.251.177";//시우집 컴퓨터 WAN 아이피랑 외부 포트
	private static final int SERVER_PORT = 9997;
	public ChatClientApp() {
		String name = null;

		name = GameLauncher.getUserDAO().getNickname();
		// 1. 소켓 만들고
		Socket socket = null;
		try {
			// 1-1 소켓 생성
			socket = new Socket();

			// 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			// 2. iostream 구현
			BufferedReader br = new BufferedReader(
							new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

			PrintWriter pw = new PrintWriter(
							new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),true);

			// 3. join 프로토콜  -> 서버가 닉네임 받아서 처리 / 성공하면 다음 실행
			pw.println("join:"+ name);
			String ack = br.readLine();  // blocking
			ChatWindow chatWindow = new ChatWindow(name,socket,ack);
			chatWindow.show();
		} catch(IOException e) {
			System.out.println("server is not exist");
		}

	}

}









