package com.javawebspringboot.websitemovie.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.javawebspringboot.websitemovie.model.EpisodeSeries;
import com.javawebspringboot.websitemovie.model.Movie;
import com.javawebspringboot.websitemovie.repository.EpisodeSeriesRepository;
import com.javawebspringboot.websitemovie.repository.MovieRepository;
import com.javawebspringboot.websitemovie.service.EpisodeSeriesService;

@Service
@Transactional
public class EpisodeSeriesServiceImpl implements EpisodeSeriesService {

	@Autowired
	private EpisodeSeriesRepository episodeSeriesRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public EpisodeSeries findByLinkEpisode(String linkEpisode) {

		return episodeSeriesRepository.findByLinkEpisode(linkEpisode);
	}

	@Override
	public void addNewEpisode(String linkMovie, MultipartFile multipartFile) {
		Movie movie = movieRepository.findByLinkMovie(linkMovie);
		Integer maxIdEpisode = episodeSeriesRepository.findTop1ByOrderByIdEpisodeDesc().getIdEpisode();
		EpisodeSeries episode = episodeSeriesRepository.findByMovie(movie);
		EpisodeSeries newEpisode = new EpisodeSeries();
		String linkEpisode = "";
		if (episode != null) {
			// da ton tai it nhat 1 tap phim
			// can stt cua tap phim
			newEpisode.setOrdinalNumbers(episode.getOrdinalNumbers());
			linkEpisode = getLinkEpisode(movie.getLinkMovie(), maxIdEpisode, episode.getOrdinalNumbers());
		} else {
			// chua co tap phim
			// mac dinh la tap 1
			newEpisode.setOrdinalNumbers(0);
			linkEpisode = getLinkEpisode(movie.getLinkMovie(), maxIdEpisode);
		}

		boolean result = saveEpisodeToDisk(linkEpisode, multipartFile);
		if (result == true) {
			newEpisode.setLinkEpisode(linkEpisode);
			newEpisode.setMovie(movie);
			newEpisode.setOrdinalNumbers(newEpisode.getOrdinalNumbers() + 1);
			episodeSeriesRepository.save(newEpisode);

		} else {
			System.out.println("Khong luu duoc tap phim vao may tinh");
		}

	}

	private boolean saveEpisodeToDisk(String linkEpisode, MultipartFile multipartFile) {
		String UPLOAD_FOLDER = System.getProperty("user.dir") + "/src/main/resources/static/video/movie/";
		try {
			byte[] bytes = multipartFile.getBytes();

			Path path = Paths.get(UPLOAD_FOLDER + "" + linkEpisode + ".mp4");
			Files.write(path, bytes);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private String getLinkEpisode(String linkMovie, Integer maxIdEpisode) {
		return linkMovie + "-tap-1-" + (maxIdEpisode + 1);
	}

	private String getLinkEpisode(String linkMovie, Integer maxIdEpisode, Integer ordinalNumbers) {
		String linkEpisode = linkMovie + "-tap-" + (ordinalNumbers + 1) + "-" + (maxIdEpisode + 1);

		return linkEpisode;
	}

	@Override
	public void downloadMovie(String linkEpisode, HttpServletResponse response) {

		try {
			File file = ResourceUtils.getFile("classpath:static/video/movie/" + linkEpisode + ".mp4");

			byte[] data = FileUtils.readFileToByteArray(file);
			// Thiết lập thông tin trả về
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			response.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
