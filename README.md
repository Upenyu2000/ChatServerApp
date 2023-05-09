# ChatServerApp

The chat server application model consists of multiple chat rooms and users. 
The server has a capacity limit and permits unique user IDs to join. The chat server admin can open and close the server and associated chat rooms. 
Users are assigned a "wantToChat" value that decreases as they perform actions, and they can move synchronously in and out of chat rooms. 
The chat rooms and server can only be closed when there are no users present. 
The model aims to enable effective management of the chat server and associated chat rooms 
by the admin while allowing users to perform various actions and leave the server when their "wantToChat" value expires.
