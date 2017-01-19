package ie.gmit.sw.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientRunner{
	
	ObjectInputStream in;
	
	UI ui = new UI();
	Scanner scan = new Scanner(System.in);
	ObjectOutputStream out; 	
	String name, user, pass, address, ban;
	int amount;
	boolean loggedIn = false;
	String message = null;		
	
	void run() throws ClassNotFoundException, IOException{
		
		try {
			Socket sock = new Socket("127.0.0.1", 2004);
			out = new ObjectOutputStream(sock.getOutputStream()); 
			out.flush();
			in = new ObjectInputStream(sock.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true){				
			
			do{// logged out
				
				ui.menu(); // calls the menu
				String command = null;
				System.out.print("Enter option: ");
				command = scan.nextLine(); 
				
				
				sendMessage(command);
				
				if(command.equals("1")){					
					
					// login
					do{
						int i = 0;
						// A while loop that continuously prompts the user to enter
						// until they enter the correct details
						do{
							message = (String)in.readObject(); // reads in the object message from the server
							System.out.println(message);
							message = scan.nextLine(); // writes in a response
							sendMessage(message); // the response is sent to the server
						
							message = (String)in.readObject(); // Username found
							
							i++;
							
							if(i == 4){
								System.out.println("Too many errors - exiting");
								System.exit(0);
							}
							
						}while(message.equals("Wrong Username"));						
						
					}while(!message.equals("Username found"));
					
					do{
						int i = 0;
						do{
							message = (String)in.readObject();
							System.out.println(message);
							message = scan.nextLine();
							
							sendMessage(message);
							message = (String)in.readObject();
							
							if(message.equals("Success")){
								loggedIn = true;
								System.out.println(message);
							}
							 
							i++;
							// if the user enters the details wrong 3 times in a row
							// the system will close
							if(i == 4){
								System.out.println("Too many errors - closing");
								System.exit(0);
							}
						
						}while(message.equals("Wrong Password"));// will continue if this message is received
					
					}while(!message.equals("Success"));// will finish when this message is received
					
					command = null; // resets command to null
				}
				
				else if(command.equals("2")){
					
					int cont = 0;
					// registration
					do{
						message = (String)in.readObject();
						System.out.println(message);
						message = scan.nextLine();
						sendMessage(message);
						
						message = (String)in.readObject();
						System.out.println(message);
						message = scan.nextLine();
						sendMessage(message);
						
						message = (String)in.readObject();
						System.out.println(message);
						message = scan.nextLine();
						sendMessage(message);
						
						message = (String)in.readObject();
						System.out.println(message);
						message = scan.nextLine();
						sendMessage(message);					
						
						message = (String)in.readObject();
						System.out.println(message);
						message = scan.nextLine();
						sendMessage(message);

						cont = 1;
						
					}while(cont != 1);
					
					command = null;
				}

				else if(command == "3"){
					System.exit(0);
				}
				
				
			}while(loggedIn == false);
			
			// ==================================================================
				
			do{// logged in
				ui.loggedIn(); // calls the menu
				String command = null;
				System.out.print("Enter option: ");
				command = scan.nextLine();
				sendMessage(command);
				
				
				if(command.equals("1")){
					
					ui.change();
					message = (String)in.readObject();
					System.out.println(message);
					message = scan.nextLine();
					sendMessage(message); 
					
					switch(message){
					
					case "1":
						message = (String)in.readObject();
						System.out.println(message);
						message = scan.nextLine();
						sendMessage(message);
						break;
						
					case "2":
						message = (String)in.readObject();
						System.out.println(message);
						message = scan.nextLine();
						sendMessage(message);
						break;
						
					case "3":
						message = (String)in.readObject();
						System.out.println(message);
						message = scan.nextLine();
						sendMessage(message);
						break;
						
					case "4":
						message = (String)in.readObject();
						System.out.println(message);
						message = scan.nextLine();
						sendMessage(message);
						break;
						
					case "5":
						message = (String)in.readObject();
						System.out.println(message);
						message = scan.nextLine();
						sendMessage(message);
						break;
						
						default:
							System.out.println("Error with entered option");
							break;
					}
					
					System.out.println(message);
					message = scan.nextLine();
					sendMessage(message);
				}
				
				else if(command.equals("2")){
					
					ui.lodgement();
					message = (String)in.readObject();
					System.out.println(message);
					message = scan.nextLine();
					sendMessage(message);
					
					if(message.equals("Over Limit")){
						System.out.println("Transaction Failed \n Over Limit of €1000");
					}
					
					message = (String)in.readObject();
					System.out.println(message);
					
				}
				
				else if(command.equals("3")){
					ui.withdrawl();
					do{	
						do{
							message = (String)in.readObject();
							System.out.println(message);
							message = scan.nextLine();
							sendMessage(message);
							
							message = (String)in.readObject();
							System.out.println(message);
						}while(message.equals("Over Limit"));
					}while(!message.equals("Successfully Withdrawn"));	
					message = (String)in.readObject();
					System.out.println(message);
				}
				
				else if(command.equals("4")){
					System.exit(0);
				}
				
			}while(loggedIn);				
		}
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException  {
		ClientRunner client = new ClientRunner();
		client.run(); // gets the client / menu running
		
	}
	
	void sendMessage(String msg) { // sending messages to the server

		try{
			 out.writeObject(msg); // writes the object out to the server
			 out.flush();	// flushes the output stream
				
		 }catch(IOException e){
				e.printStackTrace();
		 }
	 }
}
