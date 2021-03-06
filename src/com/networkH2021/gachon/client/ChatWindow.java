package com.networkH2021.gachon.client;

import com.networkH2021.gachon.GameLauncher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatWindow {

	private final Frame frame;
	private final Panel pannel;
	private final JButton buttonSend;
	private final JTextField textField;
	private final JTextArea textArea;
	private final Socket socket;
	private final String name;
	private PrintWriter pw;
	private BufferedReader br;
	private JTextPane textPane;
	private final String totalUser;
	
	
	public ChatWindow(String name, Socket socket, String ack) {
		this.socket = socket;
		this.name = name;
		this.totalUser = ack;
		frame = new Frame(name); // 큰 틀
		pannel = new Panel(); // 아래 대화 입력
		buttonSend = GameLauncher.getMainLobby().getSendbutton();
		//buttonSend = new Button("Send"); // 패널의 자식
		textField = GameLauncher.getMainLobby().getChatTextField();
		//textField = new TextField(); // 패널의 자석
		textArea = GameLauncher.getMainLobby().getChatTextArea();
		//textPane =
		//textArea = new TextArea(20, 10); // 대화 뜨는 공간;
	}

	private void finish() {
		// socket 정리
		// witer.println("QUIT")
		// thread.join();
//		if(socket != null && socket.isClosed() == false){
//			socket.close();
//		}
		System.exit(0);
	}

	public void show() throws IOException {
		// Button

		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		buttonSend.addActionListener(new ActionListener() { // 버튼이 눌려지는지 기다림
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				sendMessage();
			}
		});

		// Textfield
		textField.setColumns(30);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		}); // 엔터 쳤을 때

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		//pannel.add(textField);
		//pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		//textArea.add(scrollPane);
		//JScrollPane scrollPane = new JScrollPane(textArea);
		//frame.add(BorderLayout.CENTER, textArea);
		// Frame
		frame.setTitle("Main Chatting Room");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				finish();
			}
		});
		//frame.setVisible(tr
		frame.pack();

		// pw, br
		pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
		br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
		

		textArea.append("---------------------------------------------------------------------------------------" +
				"-------------------------------------------------------------------\n "
				+ "\t\t\t현재 참여 인원 : " + totalUser +"\n"
				+ "\t\t /? 를 치시면 사용하실 수 있는 기능이 나옵니다.\n"
				+ "---------------------------------------------------------------------------------------" +
				"-------------------------------------------------------------------\n ");
		textArea.setCaretPosition(textArea.getDocument().getLength());

		// Thread 생성
		new ReceiveThread().start();
	}

	private void updateTextArea(String message) { // thread에서 불러야함
		textArea.append(message);
		textArea.append("\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	
	private void sendMessage() {
		String message = textField.getText();
		
		String notice = "---------------------------------------------------------------------------------------" +
				"----------------------------------------------------------------\n "+
					    "\t\t\t [ 사용할 수 있는 기능 ] \n "+
					    "\t\t\t /귓속말 [상대방닉네임] : [내용]\n\n"+
					    "\t\t\t [ 방장 권한 기능 ] \n "+
					    "\t\t\t /강퇴 [상대방닉네임]      \n"+
				"---------------------------------------------------------------------------------------" +
				"----------------------------------------------------------------\n ";
		if(message.charAt(0)==('/')) {
			if(message.length()<3) {
				textArea.append(notice);
			}else if(message.startsWith("/귓속말")) {
				pw.println("wisper:" + message +":"+name);
			}else if(message.startsWith("/강퇴")) {
				pw.println("NAGA:"+message + ":" + name);
			}else {
				textArea.append(notice);
				textArea.setCaretPosition(textArea.getDocument().getLength());
			}
		}else {
			pw.println("message:" + message);
		}
		textField.setText("");
		textField.requestFocus();
	}
	
	private class ReceiveThread extends Thread {
		@Override
		public void run() {
			while(true){
				try {
					
					String reply = br.readLine(); // blocking

					if( "NAGA!".equals(reply)) {
						System.exit(0);
					}
					if (reply == null){
						break;
					}
					
					updateTextArea(reply);

				} catch (IOException e1) {
					System.exit(0);
				}
			}
		}
		
	}
}
