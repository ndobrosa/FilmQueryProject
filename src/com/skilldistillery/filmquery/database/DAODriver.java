package com.skilldistillery.filmquery.database;

import java.io.ObjectInputStream.GetField;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DAODriver {

	public static void main(String[] args) {

		new DAODriver().launch();

	}

	
	private void launch() {
		Scanner sc = new Scanner(System.in);
		DatabaseAccessorObject dao = new DatabaseAccessorObject();
		int selection = 0;

		while (selection != 5) {
			System.out.println("What would you like to do?");
			System.out.println(
					"1. Look up film by ID \n2. Look up a film by a search keyword \n3. Look up actor by ID \n4. Get a list of actors by film ID \n5. Exit");

			try {
				selection = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Wrong input type. Please give a number as input");
				new DAODriver().launch();
			} catch (RuntimeException e) {
				System.out.println("Something must've went wrong, and I don't know why. Maybe you can figure it out?");
				System.out.println(e.getStackTrace());
			}

			if (selection == 1) {
				System.out.println(dao.getFilmById(2));
			} else if (selection == 2) {
				System.out.println();
			} else if (selection == 3) {
				System.out.println(dao.getActorById(89));
			} else if (selection == 4) {
				List<Actor> actors = dao.getActorsByFilmId(4);
				System.out.println(actors);

			}

			else if (selection == 5) {
				System.out.println("Bye bye!");
			}
		}

		sc.close();
	}

}
