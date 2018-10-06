package com.skilldistillery.filmquery.database;

import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public interface DatabaseAccessor {
	public Film getFilmById(int filmId);

	public Actor getActorById(int actorId);

//  List<Film> getFilmsByActorId(int actorId);
	List<Actor> getActorsByFilmId(int actorId);

	List<Film> getFilmByKeyword(String input);
}
