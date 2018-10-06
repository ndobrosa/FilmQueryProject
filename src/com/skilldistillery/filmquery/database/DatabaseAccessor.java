package com.skilldistillery.filmquery.database;

import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.InventoryItem;

public interface DatabaseAccessor {
	public Film getFilmById(int filmId);

	public Actor getActorById(int actorId);

//  List<Film> getFilmsByActorId(int actorId);
	List<Actor> getActorsByFilmId(int filmId);

	List<Film> getFilmByKeyword(String input);

	List<InventoryItem> getInventoryItemsByFilmID(int filmID);
}
