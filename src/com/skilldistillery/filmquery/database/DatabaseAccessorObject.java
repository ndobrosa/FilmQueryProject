package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.InventoryItem;

public class DatabaseAccessorObject implements DatabaseAccessor {

	// URL to the database
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	// Static initializer that loads the JDBC driver, enabling the app to interact
	// with the database
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * The method below will take an integer and return a film object. The object is
	 * retrieved from the database. To connect to the database Connection object is
	 * created where we pass the URL, and login info to the DriverManager's static
	 * method, getConnection(...). To communicate with the database we use SQL. We
	 * use a PreparedStatement object to ensure data safety. Prepared statement's
	 * setInt method safely replaces the bound variable film.id with input (filmId
	 * in this case). We then store the results in a ResultSet object and search
	 * through the result set using ResultSet's getters. In the same lines where we
	 * use the resultSet to get info about films, we use Film's setters to store the
	 * result into appropriate variables.
	 */

	@Override
	public Film getFilmById(int filmId) {
		DatabaseAccessorObject dao = new DatabaseAccessorObject();
		Film film = null;

		// sql string will store the SQL statement
		String sql = "SELECT film.*, language.name \"language\", category.name \"category\" FROM film join language ON film.language_id = language.id join film_category ON film.id = film_category.film_id join category ON film_category.category_id = category.id WHERE film.id = ?";

		// Database login and password
		String user = "student";
		String pass = "student";

		try {
			// Connecting to the database
			Connection conn = DriverManager.getConnection(URL, user, pass);

			// Providing the SQL statement which is compiled by the database
			PreparedStatement stmt = conn.prepareStatement(sql);

			// Replacing the bound variable ('?') with the filmId value.
			stmt.setInt(1, filmId);

			// Storing the result in a ResultSet object
			ResultSet filmResult = stmt.executeQuery();

			// if the ResultSet object is not empty we create a new Film using data pulled
			// from the DB.
			if (filmResult.next()) {
				film = new Film();

				// When using the Result Set's getters, we can use column names, or use the
				// columns number, by order of columns starting with 1.
				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getString("release_year").substring(0, 4));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				film.setActors(dao.getActorsByFilmId(film.getId()));
				film.setLanguage(filmResult.getString("language"));
				film.setCategory(filmResult.getString("category"));
				film.setInventoryItems(dao.getInventoryItemsByFilmID(film.getId()));

			}

			// Close connection to the database
			filmResult.close();
			stmt.close();
			conn.close();
			dao = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return film;
	}

	// Same logic applies as to the getFilmById(int filmId) method.
	@Override
	public Actor getActorById(int actorId) {
		Actor actor = null;
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor = new Actor(); // Create the object
				// Here is our mapping of query columns to our object fields:
				actor.setId(actorResult.getInt(1));
				actor.setFirstName(actorResult.getString(2));
				actor.setLastName(actorResult.getString(3));
//				actor.setFilms(getFilmsByActorId(actorId)); // An Actor has Films

			}
			actorResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return actor;

	}

	// Similar logic applies as to the getFilmById(int filmId) method.
	@Override
	public List<Actor> getActorsByFilmId(int filmId) {
		// Since there can be many actors in a film, we will store Actor objects in a
		// list.
		List<Actor> actorList = new ArrayList<Actor>();
		String sql = "select actor.* from actor join film_actor ON actor.id = film_actor.actor_id join film ON film_actor.film_id = film.id where film.id = ?";
		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet actorResult = stmt.executeQuery();

			// While loop is used as we anticipate there may be more than one row retrieved
			// as a result of the database search.
			while (actorResult.next()) {
				Actor a = new Actor();

				a.setId(actorResult.getInt(1));
				a.setFirstName(actorResult.getString(2));
				a.setLastName(actorResult.getString(3));

				actorList.add(a);

			}
			actorResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return actorList;
	}

	// Same logic applies as to the getActorsByFilmId(int filmId) method
	@Override
	public List<InventoryItem> getInventoryItemsByFilmID(int filmId) {
		List<InventoryItem> invItems = new ArrayList<>();
		String sql = "select * from inventory_item where film_id = ?";
		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet itemResult = stmt.executeQuery();

			while (itemResult.next()) {
				InventoryItem item = new InventoryItem();

				item.setFilm_id(filmId);
				item.setId(itemResult.getInt("id"));
				item.setLast_update(itemResult.getString("last_update"));
				item.setMedia_condition(itemResult.getString("media_condition"));
				item.setStore_id(itemResult.getInt("store_id"));

				invItems.add(item);
			}
			itemResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return invItems;
	}

	// Same logic applies as to the getActorsByFilmId(int filmId) method
	// Look up a film by a search keyword
	@Override
	public List<Film> getFilmByKeyword(String input) {
		List<Film> films = new ArrayList<>();
		String sql = "SELECT film.*, language.name \"language\", category.name \"category\" FROM film join language ON film.language_id = language.id join film_category ON film.id = film_category.film_id join category ON film_category.category_id = category.id WHERE title like ? OR description like ?";

		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);

			// The '%' sign is added to the input to notify the database that text may be to
			// either right or to the left of the input String.
			stmt.setString(1, "%" + input + "%");
			stmt.setString(2, "%" + input + "%");

			ResultSet filmResult = stmt.executeQuery();
			DatabaseAccessorObject dao = new DatabaseAccessorObject();

			while (filmResult.next()) {
				Film film = new Film();

				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getString("release_year").substring(0, 4));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				film.setActors(dao.getActorsByFilmId(film.getId()));
				film.setLanguage(filmResult.getString("language"));
				film.setCategory(filmResult.getString("category"));
				film.setInventoryItems(dao.getInventoryItemsByFilmID(film.getId()));

				films.add(film);
			}
			filmResult.close();
			stmt.close();
			conn.close();
			dao = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return films;
	}

//	@Override
//	public List<Film> getFilmsByActorId(int actorId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
