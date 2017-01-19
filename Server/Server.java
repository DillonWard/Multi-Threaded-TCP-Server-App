package ie.gmit.sw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		ServerSocket ss = new ServerSocket(2004);
		Map<String, Account> users = new HashMap<String, Account>();
	
		while(true){// keeps the server running infinitely
			Socket clientSocket = ss.accept(); // assigns the client socket to the server
			ClientThread ct = new ClientThread(clientSocket, users); 
			ct.run(); // gets it running
		}
	}
}
