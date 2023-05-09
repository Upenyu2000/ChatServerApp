package com.example.chatapp;

// Is this a passive or active class?
public class User implements Runnable{

	private final int sleepScale = 100;
	
	private final int userID;
	private final ChatServer chatServer;

	private boolean joinedServer;
	private boolean joinedMainRoom;

	private int wantToChat;

	public User(int userID, ChatServer chatServer) {
		this.userID = userID;
		this.chatServer = chatServer;
		this.joinedServer = false;
		this.joinedMainRoom = false;

		// Set wantToChat to random value in range of 10 to 15.
		wantToChat = (int)(Math.random() * (15-10+1) + 10);
	}

	public int getWantToChat() {
		return wantToChat;
	}

	public int getID() {
		return userID;
	}

	// Within the run method we need to code the different actions
	// a user will take when started.
	public void run() {
		try {
			// While the user is still interested in chatting ...
			while (wantToChat > 0) {
				if (!joinedServer) {
					// Try and join Chat Server ...
					boolean joined = chatServer.addUser(this);
					if (joined) {
						joinedServer = true;
						System.out.println("User " + userID + " has joined the Chat Server!");
					} else {
						// Reduce wantToChat?

					}
				} else if (!joinedMainRoom && chatServer.getMainChatRoom() != null) {
					// Try and join Main Chat Room ...
					boolean joined = chatServer.getMainChatRoom().addUserID(this);
					if (joined) {
						joinedMainRoom = true;
						System.out.println("User " + userID + " has joined the Main Chat Room!");
					}
				} else {
					// Try and join a random Chat Room ...
					int numRooms = chatServer.getNumberOfRooms();
					int randomRoomIndex = (int)(Math.random() * numRooms);
					ChatRoom randomRoom = chatServer.getChatRoom(randomRoomIndex);
					boolean joined = randomRoom.addUserID(this);
					if (joined) {
						System.out.println("User " + userID + " has joined Chat Room " + randomRoom.getChatRoomID() + "!");
					}
				}
				// Sleep for some time ...
				Thread.sleep(wantToChat * sleepScale);
				// Decrease wantToChat ...
				wantToChat--;
			}
			// Leave all Chat Rooms and Chat Server ...
			chatServer.removeUser(this);
			System.out.println("User " + userID + " has left all Chat Rooms and Chat Server!");
		} catch (InterruptedException ex) {
			System.out.println("Interrupted User Thread (" + userID + ")");
		}
		System.out.println("User Thread (" + userID + ") has ended!");
	}

}