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

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film getFilmById(int filmId) {
		Film film = null;
		String sql =  "SELECT film.*, language.name \"language\" FROM film join language ON film.language_id = language.id WHERE film.id = ?";

		String user = "student";
		String pass = "student";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet filmResult = null;

		try {
			conn = DriverManager.getConnection(URL, user, pass);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			filmResult = stmt.executeQuery();
			if (filmResult.next()) {
				film = new Film();

				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getString("release_year"));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				film.setActors(new DatabaseAccessorObject().getActorsByFilmId(film.getId()));
				film.setLanguage(filmResult.getString("language"));
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
	public List<Actor> getActorsByFilmId(int actorId)  {
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
			stmt.setInt(1, actorId);
			actorResult = stmt.executeQuery();
			while(actorResult.next()) {
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
	
	//Look up a film by a search keyword
	@Override
	public List<Film> getFilmByKeyword(String input) {
		List<Film> films = new ArrayList<>();
		String sql = "SELECT film.*, language.name \"language\" FROM film  join language ON film.language_id = language.id WHERE title like? OR description like ?";

		String user = "student";
		String pass = "student";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet filmResult = null;

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
				film.setReleaseYear(filmResult.getString("release_year"));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				film.setActors(new DatabaseAccessorObject().getActorsByFilmId(film.getId()));
				film.setLanguage(filmResult.getString("language"));
				
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
		return films;
	}
	
	
	

//	@Override
//	public List<Film> getFilmsByActorId(int actorId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
