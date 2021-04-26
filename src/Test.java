import java.util.Scanner;

public class Test {
	static Scanner sc = new Scanner(System.in);
	
	static int processes = 5;
	static int resources = 3;
	static int [] available = {2, 5, 4};
	static int [][] maximum = {{5,5,3},{3,2,3},{8,0,2},{4,2,2},{5,3,5}};
	static int [][] allocated = {{1,1,0},{2,0,0},{2,0,1},{2,0,2},{0,1,2}};
	
	static void getInput(){
		System.out.print("Enter no. of processes: ");
		processes = sc.nextInt();  
		System.out.print("Enter no. resources: ");
		resources = sc.nextInt();  
		maximum = new int[processes][resources];
		allocated = new int[processes][resources];
		available = new int[resources];

		System.out.println("Enter allocation matrix");
		for(int i = 0; i < processes; i++)
			for(int j = 0; j < resources; j++)
				allocated[i][j] = sc.nextInt();  //allocation matrix

		System.out.println("Enter maximum matrix");
		for(int i = 0; i < processes; i++)
			for(int j = 0; j < resources; j++)
				maximum[i][j] = sc.nextInt();  //max matrix

		System.out.println("Enter available matrix");
		for(int j = 0; j < resources; j++)
			available[j] = sc.nextInt();  //available matrix
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Would you like to: \n 1) Enter your own data \n 2) Use the prepared test data? \nYour choice: ");
		int choice = sc.nextInt();
		if (choice == 1) {
			getInput();
		}
		ResourceManager banker = new ResourceManager(processes, resources, available, allocated, maximum);
		
		if (banker.safeState(true)) {
			System.out.println("The system is in a safe state\n");
		} else {
			System.out.println("The system is not in a safe state\n");
			System.exit(0);
		}
		
		String operation;
		int [] resourcesInput = new int [resources];
		int processInput;
		do {
			System.out.print("Operation (RQ / RL / Quit) : ");
			sc.nextLine();
			operation = sc.nextLine();
			if (!operation.equalsIgnoreCase("quit")) {
				System.out.print("Process : ");
				processInput = sc.nextInt();
				System.out.print("Resource : ");
				for (int i = 0; i < resources; i++) {
					resourcesInput[i] = sc.nextInt();
				}
				if (operation.equalsIgnoreCase("rq")) {
					banker.request(processInput, resourcesInput);
				}
				if (operation.equalsIgnoreCase("rl")) {
					banker.release(processInput, resourcesInput);
				}
			}
			System.out.println("\n---------------------------------------\n");
		} while (!operation.equalsIgnoreCase("quit"));
		sc.close();
	}

}
