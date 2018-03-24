package Presentation_Layer;

import java.util.Scanner;
import BusinessLayer.buisinessManager;
import DataAccessLayer.Worker;

public class Command_Line_gui {

	private buisinessManager BL;
	private Scanner in;

	public Command_Line_gui() {
		BL = buisinessManager.GetBLManager();
	}

	public buisinessManager getBL() {
		return BL;
	}

	public void run() {
		in = new Scanner(System.in);
		System.out.println("Super-S&Y Menu:\n1." + " Insert new Worker \n2." + " Update worker\n3."
				+ " Get worker details\n4." + " Exit.");
		String s = in.nextLine();
		int num;
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
			break;
		case 3:
			break;
		case 4:
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

					}
				}
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
}
