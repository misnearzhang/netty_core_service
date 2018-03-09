package com.syuct.core_service.testclient;

import com.google.gson.Gson;
import com.syuct.core_service.protoc.HandShakeMessage;
import com.syuct.core_service.protoc.TransMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

public class TestTCP {
	public static void main(String[] args) throws  InterruptedException {
		Gson gson = new Gson();
/*
		Message message=new Message();
		Header header=new Header();
		header.setType("user");
		header.setStatus("100");
		header.setUid(CommUtil.createUUID());
		message.setHead(gson.toJson(header));
		UserMessage body=new UserMessage();
		body.setSign("");
		body.setType("user");
		body.setFrom("zhanglong1490457203671");
		body.setTo("zhanglong1490457737178");
		body.setContent("hellow");
		message.setBody(gson.toJson(body));
		System.out.println(gson.toJson(message));
*/

		HandShakeMessage handShakeMessage = new HandShakeMessage();
		handShakeMessage.setAccount(System.currentTimeMillis() + "");
		handShakeMessage.setPassword("123456");
		Socket socket = null;
		OutputStream outputStream;
		InputStream inputStream;
		try {
			socket = new Socket("xcnana.com", 3000);
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
			TransMessage.Message.Builder Proto = TransMessage.Message.newBuilder();
			TransMessage.Head.Builder Head = TransMessage.Head.newBuilder();
			Head.setType(TransMessage.type.HANDSHAKE);
			Head.setStatus(TransMessage.status.REQ);
			Head.setUid(UUID.randomUUID().toString());
			Head.setTime(System.currentTimeMillis());
			Proto.setHead(Head);
			HandShakeMessage handShakeMessage1 = new HandShakeMessage();
			handShakeMessage1.setAccount("1065302407");
			handShakeMessage1.setPassword("123456");
			Proto.setBody(gson.toJson(handShakeMessage1));
			outputStream.write(Proto.build().toByteArray());
			while (true) {
				if(!socket.isConnected()) {
					System.out.println("close");
					break;
				}
				if (inputStream.available() > 0) {
					byte[] read = new byte[inputStream.available()];
					inputStream.read(read);
					TransMessage.Message message = TransMessage.Message.parseFrom(read);
					System.out.println("read---->:"+message.toString());
					//System.out.println(message.getHead().getType()+":\t:"+message.getBody());
				} else Thread.sleep(1000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	public class recevicer implements Runnable{

		Socket socket;

		public recevicer(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				InputStream in = socket.getInputStream();
				while(in.available()>0){
					in.read(new byte[in.available()]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] getSendBlock(short length,byte[] data){
		return null;
	}
}
