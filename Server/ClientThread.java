package ie.gmit.sw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class ClientThread {
	Map<String, Account> users;
	String message = null;
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	Scanner userInput;
	
	boolean loggedIn = false;
	int cont;
	String user, pass, name, address, ban, amount, change;
	String command = null;
	
	Account ac = new Account();
	
	public ClientThread(Socket clientSocket, Map<String, Account> data) {
		this.requestSocket = clientSocket;
		this.users = data; // all data is stored to a HashMap
	}

	public void run() throws IOException{		
		// initializes input/output stream
		in = new ObjectInputStream(requestSocket.getInputStream());
		out = new ObjectOutputStream(requestSocket.getOutputStream());
		out.flush();
		
		
		 do{	
			try {
				
				command = (String) in.readObject(); // reads in the message
		
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				
				do{	// by default the user is logged out			
					if(loggedIn == false){
						
						if(command.equals("1")){
							cont = 0;
							do{
								sendMessage("Enter Username: "); // sends message to the client
								user = (String)in.readObject(); // reads in the response from the client
								
								if(users.containsKey(user)){// checks if the hashmap contains the entered username
									cont = 1;
									sendMessage("Username found");
								}	
								
								else{ // sends message if wrong username entered - not found on hashmap
									sendMessage("Wrong Username");
								}
								
							}while(cont == 0);
							
							do{
								do{
									sendMessage("Enter Password: ");
									pass = (String)in.readObject();
									
									if(users.get(user).getPassword().equals(pass)){
										// checks if the password matches the password of username provided - username is key
										cont = 2;
										sendMessage("Success");	
										loggedIn = true; // controller for another menu
									}									
									else{
										sendMessage("Wrong Password"); // controller message
									}
								}while(!users.get(user).getPassword().equals(pass)); // gets the password of the user
								
							}while(cont == 1);
						}
						
						else if(command.equals("2")){
						
							// registration
							cont = 0;
							
							do{
								sendMessage("Enter Name: ");
								ac.setName((String)in.readObject());
								
								sendMessage("Enter Address: ");
								ac.setAddress((String)in.readObject());
								
								sendMessage("Enter Bank A/C Number: ");
								ac.setAccountNo((String)in.readObject());
								
								do{
									sendMessage("Enter Username - must be unique: ");
									ac.setUsername((String)in.readObject());
									
								}while(users.containsKey(user)); // continues to prompt to enter original key
															
								sendMessage("Enter Password: ");
								ac.setPassword((String)in.readObject());
								
								ac.setMoney(1000); // give default money
								
								cont = 1;
							}while(cont == 0);
							users.put(ac.getUsername(), ac);// adds a new user to the hashmap
						}
					}
					
					// ==================================================================
					
				if(loggedIn == true){
					command = ((String)in.readObject());
					 
					if(command.equals("1")){
						// change details
						System.out.println(command);
						sendMessage("What would you like to change?: ");
						String change = ((String)in.readObject());
						System.out.println("change " + change);
						
						switch(change){
						case "1":
							// change name
							sendMessage("Enter New Name: ");
							String dName = (String)in.readObject();
							users.get(user).setName(dName);
							break;
							
						case "2":
							// change address
							sendMessage("Enter New Address: ");
							String dAddr = (String)in.readObject();
							users.get(user).setAddress(dAddr);
							break;
						
						case "3":
							// change B/A number
							sendMessage("Enter New B/A Number: ");
							String dBA = (String)in.readObject();
							users.get(user).setAccountNo(dBA);
							break;
							
						case "4":
							// change Username
							sendMessage("Enter New Username: ");
							String dUser = (String)in.readObject();
							users.get(user).setUsername(dUser);
							break;
							
						case "5":
							// change password
							sendMessage("Enter New Password: ");
							String dPass = (String)in.readObject();
							users.get(user).setPassword(dPass);
							break;
						}						
					}	
					
					else if(command.equals("2")){
						System.out.println(command);
						sendMessage("Enter Amount to Lodge: ");
						String deposit = (String)in.readObject();
						double dep = Double.valueOf(deposit); // gets value of string entered
						double amount = dep + ac.getMoney(); // adds onto current users money
						ac.setMoney(amount);
						sendMessage("New Balance: " + ac.getMoney());
					}
					
					else if(command.equals("3")){

						double c = ac.getMoney();
						System.out.println(command);
						String withdraw;
						sendMessage("Enter Amount to Withdraw: ");
						withdraw = ((String)in.readObject());
						double with = Double.valueOf(withdraw);

						if(c - with < -1000){
							sendMessage("Over Limit");
						}
						else{
							sendMessage("Successfully Withdrawn");
							double amount = ac.getMoney() - with;
							ac.setMoney(amount);
							sendMessage("New Balance: " + amount);
						}
					}
				}
				}while(command == null);
			
		
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Data recieved in unknown format");
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 
		 }while(!requestSocket.isClosed());// keeps it going while the socket is not closed
	}
	
	 void sendMessage(String msg) {
			
		 try{
			 out.writeObject(msg);
			 out.flush();	
				
		 }catch(IOException e){
				e.printStackTrace();
		 }
	 }
}
