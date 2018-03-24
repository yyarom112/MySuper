package BusinessLayer;

import DataAccessLayer.Worker;
import DataAccessLayer.WorkerManager;

public class buisinessManager {
	
	private static buisinessManager instance;
	private WorkerManager workerManager;
	
	public static buisinessManager GetBLManager(){
		if(instance==null)
			instance= new buisinessManager();
		return instance;
	}
	
	private buisinessManager() {
		workerManager = WorkerManager.getWorkerManager();
		workerManager.initialWorkerTable();
	}
	
	public void addNewWorker(Worker worker) {
		workerManager.addWorkerToTable(worker);
	}
	
	public void updateWorkerInTable(Worker worker) {
		workerManager.updateTableWorker(worker);
	}
	
	public Worker getWorkerById(int ID) {
		Worker newWorker = workerManager.getWorkerByID(ID);
		return newWorker;
	}
	
	public boolean checkIfIdExist(int id) {
		int[] idList = workerManager.getListOfId();
		for(int i=0;i<idList.length;i++) {
			if(id == idList[i])
				return true;
		}
		return false;
	}

}