Running the Multiple Threaded server- client program:
1. In ServerMain.java specify the path(static variable 'dir') of the folder in your system(server directory).
2. Run the ServerMain.java.
2. In ClientMain.java specify the path(static variable 'dir') of the folder in your system(client directory).
4. Run the ClientMain.java.
5. There are four functions:
	DOWNLOAD:
		1. The server lists the files in the server side and asks the client for the file name
		2. Then the server checks the file name against the list and if the name is found, it sends the packet to the client.
		3. In the Client side the client receives the packets from the server and writes to the local directory
		
	UPLOAD:
		1. Once the user specifies the path and name of the file the client send the file to the server.
		2. The server receives these packets and creates a local copy in the server directory.
		
	RENAME:
		1. The Client sends the file name and the server checks for the file name in its directory.
		2. If found its sends the flag to get the new name ffrom the client.
		3. Then it changes the name of the file to the new name.
	
	Delete:
		1. The client sends the file name to be deleted , the server checks for the availability.
		2. If found the server deletes and sends the acknowledgement.
	
	