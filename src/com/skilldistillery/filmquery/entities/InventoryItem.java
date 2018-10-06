package com.skilldistillery.filmquery.entities;

public class InventoryItem {
	private int id;
	private int film_id;
	private int store_id;
	private String media_condition;
	private String last_update;
	
	public InventoryItem() {}

	public int getId() {
		return id;
	}
	
	

	public InventoryItem(int id, int film_id, int store_id, String media_condition, String last_update) {
		super();
		this.id = id;
		this.film_id = film_id;
		this.store_id = store_id;
		this.media_condition = media_condition;
		this.last_update = last_update;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFilm_id() {
		return film_id;
	}

	public void setFilm_id(int film_id) {
		this.film_id = film_id;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public String getMedia_condition() {
		return media_condition;
	}

	public void setMedia_condition(String media_condition) {
		this.media_condition = media_condition;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + film_id;
		result = prime * result + id;
		result = prime * result + ((last_update == null) ? 0 : last_update.hashCode());
		result = prime * result + ((media_condition == null) ? 0 : media_condition.hashCode());
		result = prime * result + store_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryItem other = (InventoryItem) obj;
		if (film_id != other.film_id)
			return false;
		if (id != other.id)
			return false;
		if (last_update == null) {
			if (other.last_update != null)
				return false;
		} else if (!last_update.equals(other.last_update))
			return false;
		if (media_condition == null) {
			if (other.media_condition != null)
				return false;
		} else if (!media_condition.equals(other.media_condition))
			return false;
		if (store_id != other.store_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\n[id=" + id + ", film_id=" + film_id + ", store_id=" + store_id + ", media_condition="
				+ media_condition + ", last_update=" + last_update + "]";
	}
	
	
	
	
	
	
}
