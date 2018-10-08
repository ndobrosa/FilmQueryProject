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
		
		// This string will store the SQL statement
		String sql = "SELECT film.*, language.name \"language\", category.name \"category\" FROM film join language ON film.language_id = language.id join film_category ON film.id = film_category.film_id join category ON film_category.category_id = category.id WHERE film.id = ?";

		// Database login and password
		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {
				film = new Film();

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
				
				filmResult.close();
				stmt.close();
				conn.close();
				dao = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return film;
	}

	@Override
	public Actor getActorById(int actorId) {
		Actor actor = null;
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		String user = "student";
		String pass = "student";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet actorResult = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor = new Actor(); // Create the object
				// Here is our mapping of query columns to our object fields:
				actor.setId(actorResult.getInt(1));
				actor.setFirstName(actorResult.getString(2));
				actor.setLastName(actorResult.getString(3));
//				actor.setFilms(getFilmsByActorId(actorId)); // An Actor has Films

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			actorResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actor;

	}

	@Override
	public List<Actor> getActorsByFilmId(int filmId) {
		List<Actor> actorList = new ArrayList<Actor>();
		String sql = "select actor.* from actor join film_actor ON actor.id = film_actor.actor_id join film ON film_actor.film_id = film.id where film.id = ?";
		String user = "student";
		String pass = "student";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet actorResult = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			actorResult = stmt.executeQuery();
			while (actorResult.next()) {
				Actor a = new Actor();

				a.setId(actorResult.getInt(1));
				a.setFirstName(actorResult.getString(2));
				a.setLastName(actorResult.getString(3));

				actorList.add(a);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			actorResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return actorList;
	}

	@Override
	public List<InventoryItem> getInventoryItemsByFilmID(int filmId) {
		List<InventoryItem> invItems = new ArrayList<>();
		String sql = "select * from inventory_item where film_id = ?";
		String user = "student";
		String pass = "student";
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet itemResult = null;

		try {
			conn = DriverManager.getConnection(URL, user, pass);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			itemResult = stmt.executeQuery();

			while (itemResult.next()) {
				InventoryItem item = new InventoryItem();

				item.setFilm_id(filmId);
				item.setId(itemResult.getInt("id"));
				item.setLast_update(itemResult.getString("last_update"));
				item.setMedia_condition(itemResult.getString("media_condition"));
				item.setStore_id(itemResult.getInt("store_id"));

				invItems.add(item);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			itemResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return invItems;
	}

	// Look up a film by a search keyword
	@Override
	public List<Film> getFilmByKeyword(String input) {
		List<Film> films = new ArrayList<>();
		String sql = "SELECT film.*, language.name \"language\", category.name \"category\" FROM film join language ON film.language_id = language.id join film_category ON film.id = film_category.film_id join category ON film_category.category_id = category.id WHERE title like ? OR description like ?";

		String user = "student";
		String pass = "student";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet filmResult = null;
		DatabaseAccessorObject dao = new DatabaseAccessorObject();
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + input + "%");
			stmt.setString(2, "%" + input + "%");
			filmResult = stmt.executeQuery();

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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dao = null;
		return films;
	}

//	@Override
//	public List<Film> getFilmsByActorId(int actorId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
