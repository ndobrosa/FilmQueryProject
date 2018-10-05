package com.skilldistillery.filmquery.database;

import com.skilldistillery.filmquery.entities.Film;

public interface DatabaseAccessor {
  public Film getFilmById(int filmId);
//  public Actor getActorById(int actorId);
//  public List<Actor> getActorsByFilmId(int filmId);
}
