package com.javawebspringboot.websitemovie.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.javawebspringboot.websitemovie.model.Country;
import com.javawebspringboot.websitemovie.model.Movie;

@Repository
@Transactional
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	@Query("select count(*) from Movie")
	int findCountRow();

	Page<Movie> findByCountry(Country country, Pageable pageable);

	List<Movie> findAllByOrderByDatetimePostDesc();

	List<Movie> findTop10ByStatusOrderByDatetimePostDesc(int status);

	List<Movie> findTop16ByStatusOrderByDatetimePostDesc(int status);

	List<Movie> findByCountry(Country country);

	Movie findByLinkMovie(String linkMovie);

	void deleteByIdMovie(Integer idMovie);
	
	
	List<Movie> findTop12ByOrderByViewDesc();

}