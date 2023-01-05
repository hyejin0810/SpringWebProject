package kosa.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/broadcasting") //클라이언트에서 서버로 접속 할 주소로 지정
public class BroadSocket {

	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

	@OnMessage //클라이언트로 부터 메시지가 도착
	public void onMessage(String message, Session session) throws IOException {
		System.out.println(message);
		synchronized (clients) {
			for (Session client : clients) {
				if (!client.equals(session)) {
					client.getBasicRemote().sendText(message);
				}
			}
		}
	}

	@OnOpen //클라이언트에서 서버로 접속
	public void onOpen(Session session) {
		System.out.println(session);
		clients.add(session);
	}

	@OnClose //접속이 끊겼을때 
	public void onClose(Session session) {
		clients.remove(session);
	}

}
