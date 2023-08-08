package com.pori.service;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.pori.config.ServerEndpointConfigurator;
import com.pori.dto.OneBoardsFormDto;
import com.pori.entity.Member;
import com.pori.entity.OneBoards;
import com.pori.repository.MemberRepository;
import com.pori.repository.OneBoardsRepository;

import jakarta.validation.Valid;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@ServerEndpoint(value = "/chat2", configurator = ServerEndpointConfigurator.class)
public class WebSocketChatt {
	
	private final OneBoardsRepository oneBoardsRepository;
	private final MemberRepository memberRepository;
	
	private static Set<Session> clients = 
			Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session s) {
		System.out.println("open session : " + s.toString());
		if(!clients.contains(s)) {
			clients.add(s);
			System.out.println("session open : " + s);
		}else {
			System.out.println("이미 연결된 session 임!!!");
		}
	}
	
	@OnMessage
	public void onMessage(String msg, Session session) throws Exception{
		System.out.println("receive message : " + msg);
		for(Session s : clients) {
			System.out.println("send data : " + msg);
			s.getBasicRemote().sendText(msg);
		}
		
	}
	
	
	@OnClose
	public void onClose(Session s) {
		
		System.out.println("session close : " + s);
		clients.remove(s);
	}
			
	
	
	
	
	
	
}
