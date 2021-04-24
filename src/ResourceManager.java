
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
	
	public void allocate() {
		
	}
	
	public void request(int process) {
		
	}
	
	public void release(int process) {
		
	}
	
	boolean safetyState() {
		return true;
	}
	
}
