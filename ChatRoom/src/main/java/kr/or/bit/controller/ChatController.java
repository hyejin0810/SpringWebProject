package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.websocket.WebSocketChat;

@Controller
@RequestMapping(value="/chat/")
public class ChatController {
	
	@RequestMapping(value="chatAll.do")
	public String getChatViewPage(Model model) {
		if(!WebSocketChat.rooms.containsKey("all")) {
			WebSocketChat.roomsList.add("all");
		}
		model.addAttribute("roomList", WebSocketChat.roomsList);
		return "chatAll";
	}
	
	@RequestMapping(value = "create.do")
	public String createRoom(String roomName, Model model) {
		System.out.println(WebSocketChat.rooms.containsKey(roomName));
		
		if(!WebSocketChat.rooms.containsKey(roomName)) {
			WebSocketChat.roomsList.add(roomName);
		}
		return "redirect:chatAll.do";
	}
	
	@RequestMapping(value="chat.do")
	public String chat(String roomName, Model model) {
		model.addAttribute("roomName", roomName);
		return "chat";
	}
}
