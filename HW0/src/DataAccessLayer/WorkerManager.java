package DataAccessLayer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;


public class WorkerManager {

	private static WorkerManager instance;
	private String dataBase;

	public static WorkerManager getWorkerManager() {
		if (instance == null)
			instance = new WorkerManager();
		return instance;
	}

	private WorkerManager() {
		dataBase="jdbc:sqlite:"+System.getProperty("user.dir")+"/DataBase.db";//TODO: fix location
	}

	private Connection connect() {
	    Connection conn = null;
	    try {
	    	conn=DriverManager.getConnection(dataBase);
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }
	    return conn;
	    }

	public void initialWorkerTable() {
		String sqlCommand = "CREATE TABLE IF NOT EXISTS WorkerTable (\n" + "	ID integer PRIMARY KEY,\n"
				+ "	FirstName VARCHAR(30) NOT NULL,\n" + "	LastName VARCHAR(30) NOT NULL,\n"
				+ "	Salary integer NOT NULL,\n" + "Check_Outdate VARCHAR(30)\n" + ");";// create the fields of the table
		try (Connection conn = DriverManager.getConnection(dataBase); Statement stmt = conn.createStatement()) {
			stmt.execute(sqlCommand);// create the worker's table
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateTableWorker(Worker worker) {
		String sqlCommand = "UPDATE WorkerTable \n" + "SET FirstName='" + worker.getFirstName() + "',\n" + "LastName='"
				+ worker.getLastName() + "',\n" + "Salary=" + worker.getSalary() + ",\n" + "Check_Outdate='"
				+ worker.getCheckOutDate() + "'\n" + "WHERE ID=" + worker.getId();
		try (Connection conn = DriverManager.getConnection(dataBase); Statement stmt = conn.createStatement()) {
			stmt.execute(sqlCommand);// create the worker's table
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void addWorkerToTable(Worker worker) {
		String sqlCommand = "Insert Into WorkerTable \n" + "(ID, FirstName, LastName, Salary,Check_Outdate)\n" + "values("
				+ "?" + "," + "?" + "," + "?" + "," + "?"+","+"?"
				+ ")";
		try (Connection conn = DriverManager.getConnection(dataBase);
                PreparedStatement pstmt = conn.prepareStatement(sqlCommand)) {
			 	pstmt.setInt(1, worker.getId());
	            pstmt.setString(2, worker.getFirstName());
	            pstmt.setString(3, worker.getLastName());
			 	pstmt.setInt(4, worker.getSalary());
	            if(worker.getCheckOutDate()!=null)
	            	pstmt.setString(5, worker.getCheckOutDate());
	            else
	            	pstmt.setString(5, " ");

	            pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public Worker getWorkerByID(int ID) {
		String sqlCommand = "SELECT ID, FirstName, LastName, Salary, Check_OutDate\n" + "From WorkerTable Where ID = "
				+ ID;
		Worker returnWorker = null;
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sqlCommand)) {
			int Id = rs.getInt("ID");
			String firstName = rs.getString("FirstName");
			String lastName = rs.getString("LastName");
			int salary = rs.getInt("Salary");
			String checkOutDate = rs.getString("Check_OutDate");
			returnWorker = new Worker(Id, firstName, lastName, salary, checkOutDate);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		return returnWorker;
	}

	public int[] getListOfId() {
		String sqlCommand = "Select ID From WorkerTable";
		int[] idList = null;
		ArrayList<Integer> tmpList = new ArrayList<Integer>();
		int counter = 0;
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sqlCommand)) {
			while (rs.next()) {
				tmpList.add(rs.getInt("ID"));
				counter++;
			}
			idList = new int[counter];
			for (int i = 0; i < counter; i++) {
				idList[i] = tmpList.get(i);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return new int[0]; // TODO:: fix
		}
		return idList;

	}
}