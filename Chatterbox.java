import java.util.Scanner;

public class Chatterbox {
    public static void main(String[] args) {
	System.out.println("Hello! I'm Chatterbox");
	System.out.println("What can I do for you?");

	Scanner scanner = new Scanner(System.in);
	String message = scanner.nextLine();

	while (!message.equals("bye")) { 
	    System.out.println(" " + message);
	    message = scanner.nextLine();
	}

	System.out.println("Bye. Hope to see you again soon!");
	scanner.close();	
    }
}

