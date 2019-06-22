package com.javawebspringboot.websitemovie.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.javawebspringboot.websitemovie.model.EpisodeSeries;

public interface EpisodeSeriesService {
	EpisodeSeries findByLinkEpisode(String linkEpisode);

	void addNewEpisode(String linkMovie, MultipartFile multipartFile);

	void downloadMovie(String linkEpisode, HttpServletResponse response);
}
