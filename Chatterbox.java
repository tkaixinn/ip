import java.util.Scanner;

public class Chatterbox {
    public static void main(String[] args) {
	System.out.println("Hello! I'm Chatterbox");
	System.out.println("What can I do for you?");

	Scanner scanner = new Scanner(System.in);
	String message = scanner.nextLine();

	String[] list = new String[100];
        int listCount = 0;

	while (!message.equals("bye")) { 
	    if (message.equals("list")) {
		for (int i = 0; i < listCount; i++) {
		    System.out.println((i + 1) + "." + list[i]);
		}
	    } else { 		
	    System.out.println("added: " + message);
	    list[listCount] = message;
            listCount++;
	    }
	message = scanner.nextLine();
	}
	System.out.println("Bye. Hope to see you again soon!");
	scanner.close();	
    }
}

