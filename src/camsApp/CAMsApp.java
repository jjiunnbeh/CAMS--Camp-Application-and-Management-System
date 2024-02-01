package camsApp;

import java.io.IOException;
import user.*;
import login.*;
import camp.*;
import java.util.*;

/**
 * Main of our system
 * Used to display the main menu after logging in successfully
 * Calles different manager to handles the different options on the menu
 * 
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class CAMsApp {

	/**
	 * Display the main menu
	 * Calls different views and manager to handle different options on the menu
	 * Each option represent a major feature of the CAMs system
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		StudentManager studentmanager = StudentManager.getInstance();
		StaffManager staffmanager = StaffManager.getInstance();
		List<Student> studentList = studentmanager.getStudents();
		List<Staff> staffList = staffmanager.getStaffs();
		printCAMs();
		LoginManager loginManager = new LoginManager(studentList, staffList);

		// Perform login choice
		User user = loginManager.login();

		if (user instanceof Staff) {
			CampEditor editor = new CampEditor((Staff) user);
			editor.displayMainMenu();
		} else if (user instanceof Student) {
			CampRegister register = new CampRegister((Student) user);
			register.displayMainMenu();

		}

		studentmanager.save();
		staffmanager.saveStaffsTxt();

	}

	/**
	 * Display the Header for our CAMs application. Enhance user interface
	 */
	private static void printCAMs() {
		System.out.println();
		System.out.println(
				"╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗");
		System.out.println(
				"║                                                                                                     ║");
		System.out.println(
				"║                                                                                                     ║");
		System.out.println(
				"║                           ▐▐▐▐▐▐      ▐▐▐▐▐▐       ▐▐▐▐▐▐▐▐▐▐▐▐▐▐       ▐▐▐▐▐▐▐▐                    ║");
		System.out.println(
				"║                          ▐▐   ▐▐▐    ▐▐    ▐▐    ▐▐▐     ▐▐     ▐▐▐    ▐▐                           ║");
		System.out.println(
				"║                          ▐▐          ▐▐▐▐▐▐▐▐    ▐▐▐     ▐▐     ▐▐▐    ▐▐▐▐▐▐▐▐                     ║");
		System.out.println(
				"║                          ▐▐          ▐▐    ▐▐    ▐▐▐     ▐▐     ▐▐▐           ▐▐                    ║");
		System.out.println(
				"║                          ▐▐   ▐▐▐    ▐▐    ▐▐    ▐▐▐     ▐▐     ▐▐▐           ▐▐                    ║");
		System.out.println(
				"║                           ▐▐▐▐▐▐     ▐▐    ▐▐    ▐▐▐     ▐▐     ▐▐▐    ▐▐▐▐▐▐▐▐                     ║");
		System.out.println(
				"║                                                                                                     ║");
		System.out.println(
				"║                             Welcome to Camp Application and Management System                       ║");
		System.out.println(
				"║                                                                                                     ║");
		System.out.println(
				"╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝");
	}
}
