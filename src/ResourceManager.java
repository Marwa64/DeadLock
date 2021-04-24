
public class ResourceManager {
	int n, m;
	int [] available;
	int [][] max;
	int [][] allocated;
	int [][] need;
	
	public ResourceManager(int processes, int resources, int [] available, int [][] allocated, int [][] maximum) {
		this.n = processes;
		this.m = resources;
		this.available = available;
		this.allocated = allocated;
		this.max = maximum;
		
		this.need = new int [n][m];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				need[i][j] = max[i][j] - allocated[i][j];
			}
		}
	}
	
	public void request(int process, int [] resources) throws InterruptedException {
		for (int i = 0; i < m; i++) {
			if (resources[i] > need[process][i]) {
				System.out.println("Process #" + process + " requested more of resource #" + i + " than it needs");
				return;
			}
			if (resources[i] > available[i]) {
				System.out.println("Not enough of resource #" + i + " available");
				return;
			}
		}
		System.out.println("\nProcess #" + process + " did not exceed the number of resources it needs");
		System.out.println("\nThere are enough resources available\n");
		
		for (int i = 0; i < m; i++) {
			available[i] -= resources[i];
			need[process][i] -= resources[i];
			allocated[process][i] += resources[i];
		}
		
		if (safeState()) {
			System.out.println("This request is safe");
		} else {
			for (int i = 0; i < m; i++) {
				available[i] += resources[i];
				need[process][i] += resources[i];
				allocated[process][i] -= resources[i];
			}
			System.out.println("This request is not safe");
		}
		
	}
	
	boolean safeState() {
		int [] work = this.available;
		boolean [] finish = new boolean [n];
		for (int i = 0; i < n; i++) {
			finish[i] = false;
		}
		
		int process = 0, resource = 0;
		while (finish[process] == false && need[process][resource] <= work[resource]) {
			// release resources
			work[resource] += allocated[process][resource];
			finish[process] = true;
			process++; resource++;
		}
		
		for (int i = 0; i < n; i++) {
			if (finish[i] == false)
				return false;
		}
		return true;
	}
	
	public void release(int process) {
		
	}
	
	
}
