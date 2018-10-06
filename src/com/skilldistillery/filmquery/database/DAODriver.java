package com.skilldistillery.filmquery.database;

import java.io.ObjectInputStream.GetField;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DAODriver {
	Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		DAODriver daoD = new DAODriver();

		daoD.launch();
		daoD.sc.close();
	}

	private void launch() {

		DatabaseAccessorObject dao = new DatabaseAccessorObject();
		DAODriver daoD = new DAODriver();
		int selection = 0;

		while (selection != 5) {
			System.out.println("What would you like to do?");
			System.out.println(
					"1. Look up film by ID \n2. Look up a film by a search keyword \n3. Look up actor by ID \n4. Get a list of actors by film ID \n5. Exit");

			try {
				selection = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Wrong input type. Please give a number as input");
				daoD.launch();
			} catch (RuntimeException e) {
				System.out.println("Something must've went wrong, and I don't know why. Maybe you can figure it out?");
				System.out.println(e.getStackTrace());
			}

			if (selection == 1) {
				daoD.getFilmById();

			} else if (selection == 2) {
				daoD.getFilmByKeyword();
			} else if (selection == 3) {
				daoD.getActorById();

			} else if (selection == 4) {
				daoD.getActorsByFilmId();
			} else if (selection == 5) {
				System.out.println("Bye bye!");
			}
		}

	}

	private void getActorsByFilmId() {
		DAODriver daoD = new DAODriver();
		DatabaseAccessorObject dao = new DatabaseAccessorObject();
		int filmId = 0;
		
		System.out.println("Please enter the film ID: ");
		try {
			filmId = sc.nextInt();
		}
		catch(RuntimeException e) {
			System.out.println("Something must've went wrong, and I don't know why. Maybe you can figure it out?");
			System.out.println(e.getStackTrace());
		}
		
		int actorsAmount = dao.getActorsByFilmId(filmId).size();
		List<Actor> actors = dao.getActorsByFilmId(filmId);
		

		if (actorsAmount == 0) {
			System.out.println("No films ware found\n");
		} else {
			System.out.println(actorsAmount + " actors were found.\n***************************************************************************************************\n");
			System.out.println(actors);		
		}
		
		
	}

	private void getFilmByKeyword() {
		DAODriver daoD = new DAODriver();
		DatabaseAccessorObject dao = new DatabaseAccessorObject();
		String keyword = null;

		System.out.println("Enter a keyword");
		try {
			keyword = sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Wrong input type. Please give a number as input");
			daoD.getFilmByKeyword();
		} catch (RuntimeException e) {
			System.out.println("Something must've went wrong, and I don't know why. Maybe you can figure it out?");
			System.out.println(e.getStackTrace());
		}

		int filmsSize = dao.getFilmByKeyword(keyword).size();

		if (filmsSize == 0) {
			System.out.println("No films ware found\n");
		} else {
			System.out.println(filmsSize + " films were found.\n***************************************************************************************************\n");
			System.out.println(dao.getFilmByKeyword(keyword));
		}

	}

	private void getFilmById() {
		DAODriver daoD = new DAODriver();
		DatabaseAccessorObject dao = new DatabaseAccessorObject();

		System.out.println("What film ID would you like tou see?");

		int filmID = 0;
		try {
			filmID = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Wrong input type. Please give a number as input");
			daoD.getFilmById();
		} catch (RuntimeException e) {
			System.out.println("Something must've went wrong, and I don't know why. Maybe you can figure it out?");
			System.out.println(e.getStackTrace());
		}

		if (dao.getFilmById(filmID) != null) {
			System.out.println(dao.getFilmById(filmID) + "\n");
		} else {
			System.out.println("There is no such film\n");
		}

	}

	private void getActorById() {
		DAODriver daoD = new DAODriver();
		DatabaseAccessorObject dao = new DatabaseAccessorObject();

		System.out.println("Please enter the actor ID: ");
		int actorID = 0;
		try {
			actorID = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Wrong input type. Please give a number as input");
			daoD.getActorById();
		} catch (RuntimeException e) {
			System.out.println("Something must've went wrong, and I don't know why. Maybe you can figure it out?");
			System.out.println(e.getStackTrace());
		}
//		daoD.getActorById();

		if (dao.getActorById(actorID) != null) {
			System.out.println(dao.getActorById(actorID));
		} else {
			System.out.println("There is no such actor\n");
		}

	}
	
	
	
//	Gave up so that I can keep the user in the case of InputMismatch in the same loop
	private int getIntInput() {
		DAODriver daoD = new DAODriver();
		
		int input = 0;
		try {
			input = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Wrong input type. Please give a number as input");
			daoD.getActorById();
		} catch (RuntimeException e) {
			System.out.println("Something must've went wrong, and I don't know why. Maybe you can figure it out?");
			System.out.println(e.getStackTrace());
		}
		
		return input;
	}

}
