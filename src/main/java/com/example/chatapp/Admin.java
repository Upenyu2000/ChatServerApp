package com.example.chatapp;// Note: This is an active class and must implement runnable
import java.util.Random;

public class Admin implements Runnable {

	private static int sleepScale = 100;

	private String name;
	private ChatServer chatServer;
	private int numOfActions = 15;  //Could be either opening or closing a chatroom

	public Admin(String name) {
		// Set the initial value of class variables
		this.name = name;
	}

	public void assignServer(ChatServer chatServer) {
		// Store given Chat Server in Class Attribute
		this.chatServer = chatServer;
	}

	public void run() {
		Random rand = new Random();

		// Open the chat server
		chatServer.open();

		// Run actions randomly
		for (int i = 0; i < numOfActions; i++) {
			try {
				Thread.sleep(rand.nextInt(sleepScale)); // Random sleep time
			} catch (InterruptedException e) {
				System.out.println("Interrupted Admin Thread (" + name + ")");
			}

			if (rand.nextBoolean()) {
				// Open a random chat room
				chatServer.openChatRoom(i+1);
			} else {
				// Close a random chat room
				chatServer.closeChatRoom(i+1);
			}
		}

		// Close the chat server and all chat rooms
		chatServer.close();

	}
}
