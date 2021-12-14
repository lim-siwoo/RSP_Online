package com.networkH2021.gachon.ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatServerReceiveThread extends Thread {

	private final Socket socket;
	private String nickname;
	public List<ChatUser> listWriters;
	PrintWriter pw = null;
	BufferedReader br = null;
	
	public ChatServerReceiveThread(Socket socket, List<ChatUser> listWriters2) {
		this.socket = socket;
		this.listWriters = listWriters2;
	}

	@Override
	public void run() {
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		System.out.println("[server] 입장 : " + remoteHostAddress + " : " + remotePort);

 		try {
 			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
 			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

			while(true) {
				String data = br.readLine();
				
				if (data == null) {
					System.out.println("사용자 종료!");
					doQuit(pw);
					break;
				}
				
//				System.out.println(">>>>>>received:" + data);
				
				// 프로토콜 분석
				String[] tokens = data.split(":");
				
				if("join".equals(tokens[0])) {
					doJoin(tokens[1], pw);
				}else if ("message".equals(tokens[0])) {
					if(tokens[1].isEmpty()==false) {
						doMessage(tokens[1]);
					}
				}else if("wisper".equals(tokens[0])){
					if (tokens.length<4) {
						pw.println("-------------------------------------------------------\n"
								+ "\t\t\t잘못된 입력입니다.\n---------------------------------------------------------------------------------------" +
								"----------------------------------------------------------------------\n ");
					}else {
						doWisper(tokens[1].substring(5), tokens[2], tokens[3]);
					}
				}else if("NAGA".equals(tokens[0])) {
					if(tokens.length<3) {
						pw.println("---------------------------------------------------------------------------------------" +
								"----------------------------------------------------------------------\n "
								+ "\t\t\t잘못된 입력입니다.\n---------------------------------------------------------------------------------------" +
								"----------------------------------------------------------------------\n ");
					}else {
						String[] who = tokens[1].split(" ");
						naga(who[1], tokens[2]);
					}
				}else {
					ChatServer.log("알수없는 요청");
				}
			}
			
		} catch (SocketException e) {
			System.out.println("[server] sudden closed by client");
			doQuit(pw);
		} catch (IOException e) { // 정상종료 안하고 확 꺼버린 ..!
			System.out.println("끄셨어요");
		} finally {
			try {
				if (socket != null && socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				System.out.println("끄셨어요2");
			}
		}
	}
	
	private void doJoin(String nickName, Writer writer) {
		this.nickname = nickName;
//		System.out.println("@@@@닉네임:" + nickName);
		// 누가 들어왔다고
		String data = "---------------------------------------------------------------------------------------" +
				"----------------------------------------------------------------------\n "+
					  "\t\t\t" + nickName + "님이 참여하셨습니다.\n"+
					  "\t\t\t현재 참여 인원 : " + (listWriters.size()+1) +"\n"+
				"---------------------------------------------------------------------------------------" +
				"----------------------------------------------------------------------\n ";

		broadcast(data, 1);
		
		// writer pool 에 현재 스레드 printWriter를 저장
		addWriter(writer, nickName);
		
		// ack 보내기
		pw.println(listWriters.size());
		pw.flush();
		
	}
	
	private void addWriter(Writer writer, String nickName) {
		synchronized (listWriters) {
//			listWriters.add(nickName, writer);
			ChatUser b = new ChatUser();
			b.setName(nickName);
			b.setWriter(writer);
			int cnt = 0;
			for(ChatUser user : listWriters) {
				if(user.isMaster() == true) {
					cnt++;
					break;
				}
			}
			if(cnt==0) {
				b.setMaster(true);
			}
			listWriters.add(b);
			
			
		}
	}
	
	private void broadcast(String data, int check) {
		synchronized (listWriters) {
			for(ChatUser user : listWriters) {
				PrintWriter pw = (PrintWriter) user.getWriter();
				
				if(check == 0) {
					pw.println(nickname + " : " +data);
				}else {
					pw.println(data);
				}
				pw.flush();
			}
		}

	}
	
	private void doMessage(String message) {
		broadcast(message, 0);
	}
	
	private void doQuit(Writer writer) {
		removeWriter(writer);
		
		String data = "---------------------------------------------------------------------------------------" +
				"----------------------------------------------------------------------\n "+
				  "\t\t\t" + this.nickname + "님이 퇴장하셨습니다.\n"+
				  "\t\t\t현재 참여 인원 : " + (listWriters.size()) +"\n"+
				"---------------------------------------------------------------------------------------" +
				"----------------------------------------------------------------------\n ";

		broadcast(data, 1);


	}
	
	private boolean checkMaster(String auMaster) {
		String checkMaster = "";
		for(ChatUser user : listWriters) {
			if(user.isMaster()==true) {
				checkMaster = user.getName();
				break;
			}
		}

		return auMaster.equals(checkMaster);
	}
	
	private void naga(String who, String auMaster) {
//		System.out.println("check master : "+checkMaster(auMaster));
		int checkNaga = 0;
		if(checkMaster(auMaster)==false) {
			pw.println("---------------------------------------------------------------------------------------" +
					"----------------------------------------------------------------------\n "
					+ "\t\t\t 권한이 없습니다.\n"
					+ "---------------------------------------------------------------------------------------" +
					"----------------------------------------------------------------------\n ");
		}else {
			for(ChatUser user : listWriters) {
				if(user.getName().equals(who)) {
					checkNaga++;
					removeWriter(user.getWriter());
					
					String data = "---------------------------------------------------------------------------------------" +
							"----------------------------------------------------------------------\n "+
							  "\t\t\t" + user.getName() + "님이 강퇴 당하셨습니다.\n"+
							  "\t\t\t현재 참여 인원 : " + (listWriters.size()) +"\n"+
							"---------------------------------------------------------------------------------------" +
							"----------------------------------------------------------------------\n ";

					broadcast(data, 1);
					((PrintWriter) user.getWriter()).println("NAGA!");
					break;
					
				}
			}
			if(checkNaga==0) {
				pw.println("---------------------------------------------------------------------------------------" +
						"----------------------------------------------------------------------\n "
						+ "\t\t\t 닉네임이 잘못 됐습니다.\n"
						+ "---------------------------------------------------------------------------------------" +
						"----------------------------------------------------------------------\n ");
			}
		}
		
		
	}
	private void doWisper(String name, String message, String me) {
		
//		System.out.println(name + ", " + message+"@@@@@@@@@@@@@@@@@");
//		출력 >> 아아, 안녕?@@@@@@@@@@@@@@@@@
		name = name.trim();
		me = me.trim();
		
		PrintWriter pw2 = null;
		for(ChatUser user : listWriters) {
			if(user.getName().equals(name)) {
//				System.out.println("알맞은 닉네임 찾아서 들어옴");
				pw2 = (PrintWriter) user.getWriter();				
			}
		}
		if(pw2 == null) {
			pw.println("---------------------------------------------------------------------------------------" +
					"----------------------------------------------------------------------\n "
					+ "\t\t사용자 닉네임을 올바르게 입력해 주세요.\n"
					+ "---------------------------------------------------------------------------------------" +
					"----------------------------------------------------------------------\n ");
		}else {
//			System.out.println(name + ", "+ message + ", "+ me + ", " + pw);
			pw.println("[ 귓속말 >>" + name + " : " + message +" ]");
			pw2.println("[ 귓속말 <<" + me + " : " + message +" ]");
			pw2.flush();			
		}

	}
	
	private void removeWriter(Writer writer) {
//		listWriters.values().removeAll(Collections.singleton(writer));
		synchronized (listWriters) {
			for(ChatUser user : listWriters) {
				if(user.getWriter().equals(writer)) {
					if(user.isMaster()==true) {
						if(listWriters.size()==1) {
							listWriters.remove(user);
							break;
						}
						listWriters.get(1).setMaster(true);
						String data ="\n★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"+
								  "\t\t " + listWriters.get(1).getName() + "님이 방장이 되셨습니다. \n"+ 
								  "★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"; 
						broadcast(data, 1);
						listWriters.remove(user);
						
						break;
					}else {
						System.out.println("@@방장 아니니까 그냥 삭제");
						listWriters.remove(user);
						break;
					}
				}
			}
			
		
		}
		
	}

}
