package com.skilldistillery.filmquery.app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
	Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		FilmQueryApp daoD = new FilmQueryApp();

		daoD.launch();
		daoD.sc.close();
	}

	private void launch() {
		FilmQueryApp daoD = new FilmQueryApp();
		int selection = 0;

		while (selection != 5) {
			System.out.println("\nWhat would you like to do?");
			System.out.println(
					"1. Look up film by ID \n2. Look up a film by a search keyword \n3. Look up actor by ID \n4. Get a list of actors by film ID \n5. Exit");
			selection = daoD.getIntInput();

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
			} else {
				System.out.println("No such option in the menu. Please try again.");
			}
		}
		
		daoD = null;
	}

	private void getActorsByFilmId() {
		FilmQueryApp daoD = new FilmQueryApp();
		DatabaseAccessorObject dao = new DatabaseAccessorObject();
		int filmId = 0;

		System.out.println("Please enter the film ID: ");
		filmId = daoD.getIntInput();

		List<Actor> actors = dao.getActorsByFilmId(filmId);

		if (actors.size() == 0) {
			System.out.println("No films were found\n");
		} else {
			System.out.println(actors.size()
					+ " actors were found.\n***************************************************************************************************\n");
			System.out.println(actors);
		}
		
		daoD = null;

	}

	private void getFilmByKeyword() {
		FilmQueryApp daoD = new FilmQueryApp();
		DatabaseAccessorObject dao = new DatabaseAccessorObject();

		System.out.println("Enter a keyword");
		String keyword = daoD.getStringInput();

		List<Film> films = dao.getFilmByKeyword(keyword);

		if (films.size() == 0) {
			System.out.println("No films ware found\n");
		} else {
			System.out.println(films.size()
					+ " films were found.\n***************************************************************************************************\n");
			System.out.println(dao.getFilmByKeyword(keyword));
		}
		
		daoD = null;

	}

	private void getFilmById() {
		FilmQueryApp daoD = new FilmQueryApp();
		DatabaseAccessorObject dao = new DatabaseAccessorObject();
		System.out.println("What film ID would you like tou see?");

		int filmID = daoD.getIntInput();
		Film film = dao.getFilmById(filmID);

		if (film != null) {
			System.out.println(film + "\n");
		} else {
			System.out.println("There is no such film\n");
		}

		System.out.println("Would you like to:\n1. Go back to main menu\n2. View full film details");
		int choice = 0;

		while (choice != 1 && choice != 2) {
			choice = daoD.getIntInput();
			if (choice == 2) {
				System.out.println(film.toFullString());
			} else if (choice == 1) {
				break;
			}
			else {
				System.out.println("wrong choice, please try again");
				System.out.println("Would you like to:\n1. Go back to main menu\n2. View full film details");
			}
		}
		daoD = null;
		dao = null;

	}

	private void getActorById() {
		FilmQueryApp daoD = new FilmQueryApp();
		DatabaseAccessorObject dao = new DatabaseAccessorObject();

		System.out.println("Please enter the actor ID: ");

		int actorID = daoD.getIntInput();

		if (dao.getActorById(actorID) != null) {
			System.out.println(dao.getActorById(actorID) + "\n");
		} else {
			System.out.println("There is no such actor\n");
		}
		daoD = null;

	}

	private int getIntInput() {
		FilmQueryApp daoD = new FilmQueryApp();

		int input = 0;
		try {
			input = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Wrong input type. Please give a number as input next time.\n");
			daoD.launch();
		} catch (RuntimeException e) {
			System.out.println(
					"Something unexpected must've went wrong, and I don't know why. Maybe you can figure it out?\n");
			System.out.println(e.getStackTrace());
		}
		daoD = null;
		return input;
	}

	private String getStringInput() {
		FilmQueryApp daoD = new FilmQueryApp();
		String input = null;

		try {
			input = sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Wrong input type. Please give a number as input");
			daoD.getFilmByKeyword();
		} catch (RuntimeException e) {
			System.out.println("Something must've went wrong, and I don't know why. Maybe you can figure it out?");
			System.out.println(e.getStackTrace());
		}
		daoD = null;
		return input;
	}

}
