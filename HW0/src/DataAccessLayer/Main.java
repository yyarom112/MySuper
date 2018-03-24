package DataAccessLayer;

public class Main {

	public static void main(String[] args) {
		WorkerManager wm=WorkerManager.getWorkerManager();
		Worker w=new Worker(204325260, "l", "l", 1, null);
		wm.addWorkerToTable(w);
	}

}
