package com.javawebspringboot.websitemovie.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "actor")
public class Actor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_actor")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idActor;

	@Column(name = "name_actor")
	private String nameActor;

	@Column(name = "id_country")
	private int idCountry;

	@Column(name = "code_actor")
	private String codeActor;

	@ManyToMany(mappedBy = "actorList")
	private List<Movie> movieList = new ArrayList<>();

	public Actor() {
		super();
	}

	public Actor(String nameActor, int idCountry, String codeActor, List<Movie> movieList) {
		super();
		this.nameActor = nameActor;
		this.idCountry = idCountry;
		this.codeActor = codeActor;
		this.movieList = movieList;
	}

	public Actor(int idActor, String nameActor, int idCountry) {
		super();
		this.idActor = idActor;
		this.nameActor = nameActor;
		this.idCountry = idCountry;
	}

	public int getIdActor() {
		return idActor;
	}

	public void setIdActor(int idActor) {
		this.idActor = idActor;
	}

	public String getNameActor() {
		return nameActor;
	}

	public void setNameActor(String nameActor) {
		this.nameActor = nameActor;
	}

	public int getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
	}

	public String getCodeActor() {
		return codeActor;
	}

	public void setCodeActor(String codeActor) {
		this.codeActor = codeActor;
	}

	public List<Movie> getMovieList() {
		return movieList;
	}

	public void setMovieList(List<Movie> movieList) {
		this.movieList = movieList;
	}

}
