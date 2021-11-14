package com.networkH2021.gachon.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientApp {
	
	private static final String SERVER_IP = "14.47.251.177";
	private static final int SERVER_PORT = 9997;

	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);

		while( true ) {
			
			System.out.println("대화명을 입력하세요.");
			System.out.print(">>> ");
			name = scanner.nextLine();
			
//			System.out.println(ChatServer.checkNickname(name));
//			if (ChatServer.checkNickname(name)==0) {
//				break;
//			}

			if (name.isEmpty() == false) {
				break;
			}


			System.out.println("사용할 수 없는 닉네임 입니다.\n");

		}

		// 1. 소켓 만들고
		Socket socket = null;
		try {
			// 1-1 소켓 생성
			socket = new Socket();
			
			// 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			
			// 2. iostream 구현
			BufferedReader br = new BufferedReader(
							new InputStreamReader(socket.getInputStream(), "utf-8"));
			
			PrintWriter pw = new PrintWriter(
							new OutputStreamWriter(socket.getOutputStream(), "utf-8"),true);
			
			// 3. join 프로토콜  -> 서버가 닉네임 받아서 처리 / 성공하면 다음 실행 
			pw.println("join:"+ name);
			String ack = br.readLine();  // blocking
//			System.out.println("------->" + ack);
			
			new ChatWindow(name, socket, ack).show(); // name이랑 소켓도 넘겨야함
		} catch(IOException e) {
			System.out.println("서버가 존재하지 않습니다.");
		} finally {
			scanner.close();
		}

	}

}









