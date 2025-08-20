import java.util.Scanner;

public class Chatterbox {
    public static void main(String[] args) {
	System.out.println("Hello! I'm Chatterbox");
	System.out.println("What can I do for you?");

	Scanner scanner = new Scanner(System.in);

	Task[] list = new Task[100];
        int listCount = 0;


	while (scanner.hasNextLine()) {
		String message = scanner.nextLine();
	
	try {
	     if (message.equals("todo")) {
	         throw new TodoException("SORRY!!! No empty description for a todo");
	      }

	     if (message.equals("deadline")) {
		 throw new DeadlineException("SORRY!!! No empty description for a deadline");
	     }

	     if (message.equals("event")) {
		 throw new EventException("SORRY!!! No empty description for an event");
	     }
	
	 } catch (TodoException | DeadlineException |EventException e) {
	          System.out.println(e.getMessage());
		  continue;
 	 }
	     

	     if (message.equals("bye")) {
	         System.out.println("Bye. Hope to see you again soon!");
		 System.out.flush();
		 break;
		}

	    if (message.equals("list")) {
		System.out.println("Here are the tasks in your list:");
		for (int i = 0; i < listCount; i++) {
		    System.out.println((i + 1) + "." + list[i]);
		}

	    } else if (message.contains("mark")) {
		String[] parts = message.split("\\s+");
		    int trueTaskIndex = Integer.parseInt(parts[1]) - 1;
                    Task trueTask = list[trueTaskIndex];
    
                if (parts[0].equals("mark")) {
		    trueTask.isDone = true;
		    System.out.println("Nice! I've marked this task as done:");
		    System.out.println(trueTask);
		} else if (parts[0].equals("unmark")) {
		    trueTask.isDone = false;
		    System.out.println("OK, I've marked this task as not done yet:");
		    System.out.println(trueTask);
		}
	    } else {
			Task currentTask = null;

			if (message.contains("todo")) {
				String[] parts = message.split("\\s", 2);
				currentTask = new Todo(parts[1]);

			} else if (message.contains("deadline")) {
				String[] parts = message.split("\\s", 2);
				currentTask = new Deadline(parts[1]);

			} else if (message.contains("event")) {
				String[] parts = message.split("/", 3);
				String[] taskName = parts[0].split("\\s", 2);
				currentTask = new Event(taskName[1], parts[1], parts[2]);
			} else {
				try {
			            throw new NilException("SORRY!!! I don't know what that means");
				} catch (NilException e) {
				    System.out.println(e.getMessage());
				    continue;
				}    
			} 

			if (currentTask != null) {
				System.out.println("Got it. I've added this task:");
				System.out.println(currentTask);
				list[listCount] = currentTask;
				listCount++;
				System.out.println("Now you have " + listCount + " tasks in the list.");
			}
		}
	}
	scanner.close();	
    }
}

