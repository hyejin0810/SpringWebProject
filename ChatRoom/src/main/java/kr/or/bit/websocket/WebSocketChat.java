package kr.or.bit.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint(value="/chat.do/{roomName}/{userName}") // /echo.do 라는 url 요청을 통해 웹소켓에 들어가겠다
public class WebSocketChat {
	
	public static List<String> roomsList = new ArrayList<String>(); //방리스트
	public static Map<String, List<Session>> rooms = new HashMap<String, List<Session>>(); //해당 채팅방 접속자목록저장
	
	@OnOpen //클라이언트가 웹소켓에 문제없이 들어왔을때 실행
	public void onOpen(Session session, @PathParam("roomName") String roomName, @PathParam("userName") String userName) {
		
		try {
			
			if(!rooms.containsKey(roomName)) {//map에 방이름을 찾아 list 가 없으면
				rooms.put(roomName, new ArrayList<Session>()); //채팅방 생성
			}
			rooms.get(roomName).add(session);
			session.getUserProperties().putIfAbsent("roomName", roomName); //유저설정 셋팅
			session.getUserProperties().putIfAbsent("userName", userName);
			
			final Basic basic = session.getBasicRemote();
			basic.sendText("<b>["+roomName + "] 방에 연결되었습니다.<br>현재접속자 : " + rooms.get(roomName).size()+"</b>");
			sendAllSessionToMessage(session, userName+"님이 접속되었습니다.,system");
			
			System.out.println("rooms : " + roomName + " / 인원수 : " + rooms.get(roomName).size());
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}		
	}
	
	//메시지 보낸 자신을 제외한 나머지 연결된 세션에 메시지를 보냄
	private void sendAllSessionToMessage(Session self, String message) {
		try {
			String room = (String) self.getUserProperties().get("roomName");
			System.out.println("room : " + room);
			
			for(Session session : rooms.get(room)) {
				if(!self.getId().equals(session.getId())) {
					session.getBasicRemote().sendText(message.split(",")[1] + " : " + message.split(",")[0]);
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@OnMessage //메시지가 들어왔을때 실행
	public void onMessage(String message, Session session) {
		System.out.println("Message From " + message.split(",")[1] + ": " + message.split(",")[0]);
		
		String sender = message.split(",")[1];
		String msg = message.split(",")[0];
		
		try {
			final Basic basic = session.getBasicRemote();
			
			if(msg.equals("/list")) {
				String room = (String) session.getUserProperties().get("roomName");
				String users = "<b>[접속자 목록]<br>";
				for(Session user : rooms.get(room)) {
					users += user.getUserProperties().get("userName") + "<br>";
				}
				users += "</b>";
				basic.sendText(users);
			}else {
				basic.sendText("to : " + msg);
				sendAllSessionToMessage(session, message);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@OnError
	public void onError(Throwable e, Session session) {
		
	}
	
	@OnClose //클라이언트와 웹소켓 연결이 끊기면 실행
	public void onClose(Session session) {
		
		System.out.println("Session " + session.getId() + "접속종료");
		String room = (String) session.getUserProperties().get("roomName");
		String user = (String) session.getUserProperties().get("userName");
		sendAllSessionToMessage(session, user+"님 접속이 종료되었습니다.,system");
		rooms.get(room).remove(session);
	}
}
