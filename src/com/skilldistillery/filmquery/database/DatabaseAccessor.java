package com.skilldistillery.filmquery.database;

import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.InventoryItem;

// Defines public abstract methods that every class implementing DatabaseAccessor will need to explicitly implement (define)
public interface DatabaseAccessor {

	Film getFilmById(int filmId);

	Actor getActorById(int actorId);

	List<Actor> getActorsByFilmId(int filmId);

	List<Film> getFilmByKeyword(String input);

	List<InventoryItem> getInventoryItemsByFilmID(int filmID);
}
