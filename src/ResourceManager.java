
public class ResourceManager {
	int p, r;
	int [] available;
	int [][] max;
	int [][] allocated;
	int [][] need;
	
	public ResourceManager(int processes, int resources, int [] available, int [][] allocated, int [][] maximum) {
		this.p = processes;
		this.r = resources;
		this.available = available;
		this.allocated = allocated;
		this.max = maximum;
		
		this.need = new int [p][r];
		
		for (int i = 0; i < p; i++) {
			for (int j = 0; j < r; j++) {
				need[i][j] = max[i][j] - allocated[i][j];
			}
		}
		
		printData();
	}
	
	public void printData() {
		System.out.println("Allocation matrix");
		for(int i = 0; i < p; i++) {
			for(int j = 0; j < r; j++) {
				System.out.print(allocated[i][j] + " ");
			}
			System.out.println();
		}


		System.out.println("Maximum matrix");
		for(int i = 0; i < p; i++) {
			for(int j = 0; j < r; j++) {
				System.out.print(max[i][j] + " ");
			}
			System.out.println();
		}


		System.out.println("Available matrix");
		for(int j = 0; j < r; j++) {
			System.out.print(available[j] + " ");
		}
		System.out.println();
			
		System.out.println("Need matrix");
		for(int i = 0; i < p; i++) {
			for(int j = 0; j < r; j++) {
				System.out.print(need[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("\n---------------------------------------\n");
	}
	
	public void request(int process, int [] resources) throws InterruptedException {
		for (int i = 0; i < r; i++) {
			if (resources[i] > need[process][i]) {
				System.out.println("\nProcess #" + process + " requested more of resource #" + i + " than it needs");
				return;
			}
			if (resources[i] > available[i]) {
				System.out.println("\nNot enough of resource #" + i + " available");
				return;
			}
		}
		System.out.println("\nProcess #" + process + " did not exceed the number of resources it needs");
		System.out.println("\nThere are enough resources available\n");
		
		for (int i = 0; i < r; i++) {
			available[i] -= resources[i];
			need[process][i] -= resources[i];
			allocated[process][i] += resources[i];
		}
		
		if (safeState(false)) {
			System.out.println("This request is safe, resources have been allocated\n");
			printData();
		} else {
			for (int i = 0; i < r; i++) {
				available[i] += resources[i];
				need[process][i] += resources[i];
				allocated[process][i] -= resources[i];
			}
			System.out.println("This request is not safe, resourced allocation has been denied\n");
		}
		
	}
	
	boolean checkResources(int process, int [] work) {
		for (int j = 0; j < r; j++) {
			if (need[process][j] > work[j]) {
				return false;
			}
		}
		return true;
	}
	
	public boolean safeState(boolean beginning) {
		int [] work = new int[r];
		for (int i = 0; i < r; i++) {
			work[i] = available[i];
		}
		
		boolean [] finish = new boolean [p];
		for (int i = 0; i < p; i++) {
			finish[i] = false;
		}
		
		for (int i = 0; i < p; i++) {
			if (finish[i] == false && beginning) System.out.println();
			if (finish[i] == false && checkResources(i, work)) {
				if (beginning) System.out.print("Process: #" + i + " : ");
				for (int j = 0; j < r; j++) {
					work[j] += allocated[i][j];
					if (beginning) System.out.print(work[j] + "  ");
				}
				finish[i] = true;
				i = -1;
			}
		}
		
		System.out.println();
		
		for (int i = 0; i < p; i++) {
			if (finish[i] == false)
				return false;
		}
		return true;
	}
	
	public void release(int process, int [] resources) {
		for (int i = 0; i < r; i++) {
			if (resources[i] > allocated[process][i]) {
				System.out.println("\nProcess #" + process + " can't release more of resource #" + i + " than it has allocated");
				return;
			}
		}
		for (int i = 0; i < r; i++) {
			allocated[process][i] -= resources[i];
			available[i] += resources[i];
		}
		System.out.println("\nResources have been released\n");
		printData();
	}
}
