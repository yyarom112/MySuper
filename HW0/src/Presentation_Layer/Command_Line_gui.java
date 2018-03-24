package Presentation_Layer;

import java.util.Scanner;
import BusinessLayer.buisinessManager;
import DataAccessLayer.Worker;

public class Command_Line_gui {

	private buisinessManager BL;	//connection to the business Layer
	private Scanner in;

	public Command_Line_gui() {
		BL = buisinessManager.GetBLManager();
	}

	public buisinessManager getBL() {
		return BL;
	}
	//the main program of the presentation layer.
	public void run() {
		in = new Scanner(System.in);
		System.out.println("Super-S&Y Menu:\n1." + " Insert new Worker \n2." + " Update worker\n3."
				+ " Get worker details\n4." + " Exit.");
		String s = in.nextLine();
		int num;
		//the user is not insert correct input.
		while ((num = cutNumString(s)) == -1) {
			System.out.println("Illegal input, please try again.\n\n");
			System.out.println("Super-S&Y Menu:\n1." + " Insert new Worker \n2." + " Update worker\n3."
					+ " Get worker details\n4." + " Exit.");
			s = in.nextLine();
		}
		switch (num) {
		case 1:
			addWorkerToTable();
			break;
		case 2:
			UpdateWorker();
			break;
		case 3:
			getWorkerDetails();
			break;
		case 4:
			System.exit(0);
			break;

		}

	}

	
	private int cutNumString(String s) {
		if (s.length() == 0 || s.length() > 1 || s.charAt(0) < 49 || s.charAt(0) > 52)
			return -1;
		else {
			int output = Character.getNumericValue(s.charAt(0));
			if (output >= 1 && output <= 4)
				return output;
			return -1;
		}
	}

	
	private void addWorkerToTable() {
		int id;
		String fname, lname;
		int salery;
		System.out.println("Insert worker menu:\nEnter worker ID.\nIf you wish to return to the main Menu press ~ ");
		String s = in.nextLine();
		while (!checkForExit(s) && cutID(s) == -1) {
			System.out.println("Illegal input, please try again.\n\n");
			s = in.nextLine();
		}
		if (checkForExit(s)) {
			run();
		} else {
			id = cutID(s);

			if (BL.checkIfIdExist(id)) {
				System.out.println("The worker already exist\n");
				run();
			} else {
				System.out.println("Enter first name\nIf you wish to return to the main Menu press ~");
				fname = in.nextLine();
				while (!(checkForExit(fname)) && !(cheackStringOnlyLetters(fname))) {
					System.out.println("Illegal input, please try again.");
					fname = in.nextLine();
				}
				if (checkForExit(fname))
					run();
				else {
					System.out.println("Enter last name\nIf you wish to return to the main Menu press ~");
					lname = in.nextLine();
					while (!(checkForExit(lname)) && !(cheackStringOnlyLetters(lname))) {
						System.out.println("Illegal input, please try again.");
						lname = in.nextLine();
					}
					if (checkForExit(lname))
						run();
					else {
						System.out.println("Enter worker salary\nIf you wish to return to the main Menu press ~");
						s = in.nextLine();
						while (!(checkForExit(s)) && !(cheackStringOnlyNum(s.trim()))) {
							System.out.println("Illegal input, please try again.");
							s = in.nextLine();
						}
						if (checkForExit(s))
							run();
						salery = Integer.parseInt(s);
						BL.addNewWorker(new Worker(id, fname, lname, salery, null));
						System.out.println("Worker added successfully");
						run();
					}
				}
			}
		}
	}

	private void UpdateWorker() {
		int id;
		System.out.println("Update worker menu:\nEnter worker ID.\nIf you wish to return to the main Menu press ~ ");
		String s = in.nextLine();
		while (!checkForExit(s) && cutID(s) == -1) {
			System.out.println("Illegal input, please try again.");
			s = in.nextLine();
		}
		if (checkForExit(s))
			run();
		else {
			id = Integer.parseInt(s);
			if (!BL.checkIfIdExist(id)) {
				System.out.println("The worker does not exist\n");
				run();
			} else {
				Worker worker = BL.getWorkerById(id);
				System.out.println(
						"1. Update first name.\n2. Update last name.\n3. Update salary.\n4. Update dismissal date.\n5. Main Menu.");
				s = in.nextLine();
				while (s.length() > 1 || (s.charAt(0) < 49 && s.charAt(0) > 53)) {
					System.out.println("Illegal input, please try again.");
					s = in.nextLine();
				}

				switch (s.charAt(0)) {
				case ('1'): {
					System.out.println("Enter first name.\nIf you wish to return to the main Menu press ~");
					String s3 = in.nextLine();
					while (!(checkForExit(s3)) && !cheackStringOnlyLetters(s3)) {
						System.out.println("Illegal input, please try again.");
						s3 = in.nextLine();
					}
					if (checkForExit(s3))
						run();
					else {
						worker.setFirstName(s3);
						BL.updateWorkerInTable(worker);
					}
					break;
				}
				case ('2'): {
					System.out.println("Enter last name.\nIf you wish to return to the main Menu press ~");
					String s3 = in.nextLine();
					while (!(checkForExit(s3)) && !cheackStringOnlyLetters(s3)) {
						System.out.println("Illegal input, please try again.");
						s3 = in.nextLine();
					}
					if (checkForExit(s3))
						run();
					else {
						worker.setLastName(s3);
						BL.updateWorkerInTable(worker);
					}
					break;
				}
				case ('3'): {
					System.out.println("Enter salary.\nIf you wish to return to the main Menu press ~");
					String s3 = in.nextLine();
					while (!(checkForExit(s3)) && !cheackStringOnlyNum(s3)) {
						System.out.println("Illegal input, please try again.");
						s3 = in.nextLine();
					}
					if (checkForExit(s3))
						run();
					else {
						worker.setSalary(Integer.parseInt(s3));
						BL.updateWorkerInTable(worker);
						break;
					}
				}
				case ('4'): {
					System.out.println(
							"Enter dismissal date with format dd.MM.yyyy .\nIf you wish to return to the main Menu press ~");
					String s3 = in.nextLine();
					while (!(checkForExit(s3)) && !checkDate(s3)) {
						System.out.println("Illegal input, please try again.");
						s3 = in.nextLine();
					}
					if (checkForExit(s3))
						run();
					else {
						worker.setCheckOutDate(s3);
						BL.updateWorkerInTable(worker);
						break;
					}
				}
				case ('5'):
					run();
					break;
				}
			}
			UpdateWorker();
		}
	}

	private void getWorkerDetails() {
		int id;
		System.out.println("Update worker menu:\nEnter worker ID.\nIf you wish to return to the main Menu press ~ ");
		String s = in.nextLine();
		while (!checkForExit(s) && cutID(s) == -1) {
			System.out.println("Illegal input, please try again.");
			s = in.nextLine();
		}
		if (checkForExit(s))
			run();
		else {
			id = Integer.parseInt(s);
			if (!BL.checkIfIdExist(id)) {
				System.out.println("The worker does not exist\n");
				run();
			} else {
				Worker worker = BL.getWorkerById(id);
				String output = "Worker full name: " + worker.getFirstName() + " " + worker.getLastName() + ".\n"
						+ "Worker ID: " + worker.getId() + ".\n" + "Worker salary: " + worker.getSalary() + ".\n";
				if (worker.getCheckOutDate() != null || checkDate(worker.getCheckOutDate()))
					output = output + "Worker dismissal date: " + worker.getCheckOutDate() + ".\n";
				System.out.println(output);
				getWorkerDetails();
			}
		}
	}

	private boolean checkForExit(String s) {
		s = s.trim();
		if (s.length() == 1 && s.charAt(0) == '~')
			return true;
		else
			return false;
	}

	private int cutID(String s) {
		s = s.trim();
		if (s.length() != 9)
			return -1;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) < 48 || s.charAt(i) > 58)
				return -1;
		}
		return Integer.parseInt(s);
	}

	private boolean cheackStringOnlyLetters(String s) {
		return s.chars().allMatch(Character::isLetter);
	}

	private boolean cheackStringOnlyNum(String s) {
		s = s.trim();
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) < 48 || s.charAt(i) > 57)
				return false;
		}
		return true;
	}

	private boolean checkDate(String s) {
		if (s.length() != 10)
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 2 || i == 5) {
				if (s.charAt(i) != '.')
					return false;
			} else {
				if (s.charAt(i) < 48 && s.charAt(i) > 57)
					return false;
			}
		}
		String temp1 = s.substring(0, 2);
		if (Integer.parseInt(temp1) > 31 || Integer.parseInt(temp1) < 0)
			return false;
		String temp2 = s.substring(3, 5);
		if (Integer.parseInt(temp2) > 12 || Integer.parseInt(temp2) < 0)
			return false;
		String temp3 = s.substring(6);
		if (Integer.parseInt(temp3) < 0)
			return false;
		return true;
	}
}
