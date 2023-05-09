package com.example.chatapp;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {

	private int chatRoomID;
	private int capacity;
	private List<User> users;
	private boolean isOpen;

	public ChatRoom(int chatRoomID, int capacity) {
		// Set the initial value of class variables.
		// Think carefully about how to protect users from
		// unintended synchronous activity.
		this.chatRoomID = chatRoomID;
		this.capacity = capacity;
		this.users = new ArrayList<>();
		this.isOpen = true;
	}
	//All getters and setters for the constructor
	public boolean getIsOpen() {
		return isOpen;
	}


	public List<User> getUsers() {
		return users;
	}
	public int getCapacity() {
		return capacity;
	}

	public boolean addUserID(User user) {
		this.users.add(user);
		return false;
	}

	public int getChatRoomID() {
		return chatRoomID;
	}
	public void removeUser (User user){
		this.users.remove(user);
	}

	public synchronized boolean enterRoom(User user) {
		if (!isOpen) {
			return false;
		}
		if (users.size() >= capacity) {
			return false;
		}
		if (users.contains(user)) {
			return false;
		}
		users.add(user);
		System.out.println("User " + user.getID() + " joined Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
		return true;
	}

	public synchronized void leaveRoom(User user) {
		System.out.println("User " + user.getID() + " left Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
		users.remove(user);

		if (users.isEmpty()) {
			isOpen = false;
		}
	}

	public synchronized void open() {
		isOpen = true;
		System.out.println("Chat Room " + chatRoomID + " open!");
	}

	public synchronized void close() {
		if (!users.isEmpty()) {
			System.out.println("Can't close Chat Room " + chatRoomID + " because there are still users in the room.");
			return;
		}
		isOpen = false;
		System.out.println("Chat Room " + chatRoomID + " closed!");
	}
}
