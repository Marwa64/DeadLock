
public class Test {

	public static void main(String[] args) throws InterruptedException {
		int processes = 5, resources = 3;
		int [] available = {2, 5, 4};
		int [][] maximum = {{5,5,3},{3,2,3},{8,0,2},{4,2,2},{5,3,5}};
		int [][] allocated = {{1,1,0},{2,0,0},{2,0,1},{2,0,2},{0,1,2}};
		
		ResourceManager banker = new ResourceManager(processes, resources, available, allocated, maximum);
		int [] req = {2, 0, 1};
		banker.request(4, req);
	}

}
