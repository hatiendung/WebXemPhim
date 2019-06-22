package com.javawebspringboot.websitemovie.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javawebspringboot.websitemovie.model.EpisodeSeries;
import com.javawebspringboot.websitemovie.model.Movie;

@Repository
@Transactional
public interface EpisodeSeriesRepository extends JpaRepository<EpisodeSeries, Integer> {
	EpisodeSeries findByLinkEpisode(String linkEpisode);
	EpisodeSeries findTop1ByOrderByIdEpisodeDesc();
	EpisodeSeries findByMovie(Movie movie);
}
