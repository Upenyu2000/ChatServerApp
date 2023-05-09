package com.example.chatapp;

import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private List<ChatRoom> rooms;
	private List<User> users;
	private Admin admin;
	private ChatRoom mainRoom;
	private int capacity;
	private boolean isOpen;

	public ChatServer(int capacity, int numOfRooms, Admin admin) {
		this.rooms = new ArrayList<>();
		for (int i = 0; i < numOfRooms; i++) {
			this.rooms.add(new ChatRoom(i, capacity));
		}
		this.users = new ArrayList<>();
		this.admin = admin;
		this.admin.assignServer(this);
		this.capacity = capacity;
		this.isOpen = false;
	}

	public synchronized boolean open() {
		if (isOpen) {
			System.out.println("Chat Server is already open.");
			return false;
		}
		isOpen = true;
		System.out.println("Chat Server is opened.");
		return true;
	}

	public synchronized void close() {
		if (!isOpen) {
			System.out.println("Chat Server is already closed.");
			return;
		}
		for (ChatRoom room : rooms) {
			if (room.getIsOpen()) {
				System.out.println("Chat Room " + room.getChatRoomID() + " is being closed.");
				room.close();
			}
		}
		isOpen = false;
		System.out.println("Chat Server is closed.");
	}

	public synchronized boolean join(User user) {
		if (!isOpen) {
			System.out.println("Chat Server is not open.");
			return false;
		}
		if (users.size() >= capacity) {
			System.out.println("Chat Server is full.");
			return false;
		}
		if (isOpen && users.size() < capacity){
			users.add(user);
			System.out.println("User " + user.getID() + " is admitted to Chat Server (" + user.getWantToChat() + ").");
			return true;
		} else {
			System.out.println("User " + user.getID() + " failed to join Chat Server (" + user.getWantToChat() + ").");
			return false;
		}

	}

	public synchronized boolean leave(User user) {
		if (!isOpen) {
			System.out.println("Chat Server is not open.");
			return false;
		}
		if (!users.contains(user)) {
			System.out.println("User " + user.getID() + " is not in the Chat Server.");
			return false;
		} else {
			users.remove(user);
			System.out.println("User " + user.getID() + " left the Chat Server.");
			return true;
		}
	}

	public void openChatRoom(int chatRoomID) {
		if (chatRoomID < 0 || chatRoomID >= rooms.size()) {
			System.out.println("Chat Room ID " + chatRoomID + " is invalid.");
			return;
		}
		ChatRoom room = rooms.get(chatRoomID);
		if (room.getIsOpen()) {
			System.out.println("Chat Room " + chatRoomID + " is already open.");
			return;
		}
		room.open();
	}

	public void closeChatRoom(int chatRoomID) {
		if (chatRoomID < 0 || chatRoomID >= rooms.size()) {
			System.out.println("Chat Room ID " + chatRoomID + " is invalid.");
			return;
		}
		ChatRoom room = rooms.get(chatRoomID);
		if (!room.getIsOpen()) {
			System.out.println("Chat Room " + chatRoomID + " is already closed.");
			return;
		}
		room.close();
	}

	public boolean enterRoom(User user, int chatRoomID) {
		if (!isOpen) {
			System.out.println("Chat Server is not open.");
			return false;
		}
		if (!users.contains(user)) {
			System.out.println("User " + user.getID() + " is not in the Chat Server.");
			return false;
			// Code to allow user to leave Chat Room.
		}
		ChatRoom room = rooms.get(chatRoomID);
		if (!room.getIsOpen()) {
			System.out.println("Chat Room " + chatRoomID + " is not open.");
			return false;
		}
		if (room.getUsers().size() >= room.getCapacity()) {
			System.out.println("Chat Room " + chatRoomID + " is at maximum capacity.");
			return false;
		}
		if (room.getUsers().contains(user)) {
			System.out.println("User " + user.getID() + " is already in Chat Room " + chatRoomID + ".");
			return false;
		}
		room.enterRoom(user);
		room.addUserID(user);
		return true;
	}

	public void leaveRoom(User user, int chatRoomID) {
		ChatRoom room = rooms.get(chatRoomID);
		if (!room.getIsOpen()) {
			System.out.println("Chat Room " + chatRoomID + " is not open.");
			return;
		}
		if (!room.getUsers().contains(user)) {
			System.out.println("User " + user.getID() + " is not in Chat Room " + chatRoomID + ".");
			return;
		}
		System.out.println("User " + user.getID() + " has left Chat Room " + chatRoomID + ".");
		room.removeUser(user);
	}


	public int getNumberOfRooms() {
		return rooms.size();
	}

	public boolean isRoomOpen(int chatRoomID) {
		return rooms.get(chatRoomID).getIsOpen();
	}

	public int getNumberOfUsers() {
		return users.size();
	}


	public ChatRoom getChatRoom(int randomRoomIndex) {
		if (randomRoomIndex < 0 || randomRoomIndex >= rooms.size()) {
			throw new IllegalArgumentException("Invalid chat room index: " + randomRoomIndex);
		}
		return rooms.get(randomRoomIndex);
	}


	public boolean addUser(User user) {
		this.users.add(user);
		return false;
	}

	public ChatRoom getMainChatRoom() {
		return mainRoom;
	}

	public void removeUser (User user){
		this.users.remove(user);
	}

}