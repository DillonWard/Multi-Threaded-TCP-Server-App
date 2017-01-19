package ie.gmit.sw.client;

public class UI {

	boolean cont;
	
	public UI(){
		this.cont = true;
	}
	
	public void menu(){
		System.out.println("====== Banking Client =======");
		System.out.println("1 - Login");
		System.out.println("2 - Register");
		System.out.println("3 - Quit");
	}
	
	public void loggedIn(){

		System.out.println("1 - Change Customer Details");
		System.out.println("2 - Make Lodgement");
		System.out.println("3 - Make Withdrawl (Limit of €1000)");
		System.out.println("4 - Exit");
	}
	
	public void login(){
		System.out.println("Logging into Banking system.");
	}
	
	public void register(){
		System.out.println("Registering with the System.");
	}
	
	public void change(){
		System.out.println("Changing Customer Details");
		System.out.println("1 - Change Name");
		System.out.println("2 - Address");
		System.out.println("3 - Bank A/C Number");
		System.out.println("4 - Username");
		System.out.println("5 - Password");
	}
	
	public void lodgement(){
		System.out.println("Making Lodgement");
	}
	
	public void withdrawl(){
		System.out.println("Making Withdrawal");
	}
	
	public boolean isActive(){
		return cont;
	}
	
}
