package com.javawebspringboot.websitemovie.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javawebspringboot.websitemovie.model.Actor;
import com.javawebspringboot.websitemovie.service.ActorService;
import com.javawebspringboot.websitemovie.service.SlideService;
import com.javawebspringboot.websitemovie.utils.CustomActor;

@Controller
public class WebAPI {

	@Autowired
	private SlideService slideService;

	@Autowired
	private ActorService actorService;

	@RequestMapping(value = "/api/ajax/idSlide/{idSlide}", method = RequestMethod.GET)
	@ResponseBody
	public void ajax(@PathVariable(name = "idSlide") Integer idSlide) {

		slideService.updateSlideByStatus(idSlide);
	}

	@RequestMapping(value = "/api/download-movie/{linkEpisode}", method = RequestMethod.GET)
	public void downloadMovie(@PathVariable(name = "linkEpisode") String linkEpisode, HttpServletResponse response)
			throws Exception {
		String VIDEO_MOVIE = System.getProperty("user.dir") + "/src/main/resources/static/video/movie/";
		File file = new File(VIDEO_MOVIE + "test.pdf");
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
		BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = inStrem.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		outStream.flush();
		inStrem.close();

	}

	@RequestMapping("/api/search-actor")
	public @ResponseBody List<CustomActor> searchrActor(@RequestParam String keyWord) {
		List<Actor> listActor = actorService.searchActor(keyWord);
		List<CustomActor> listCustomActor = new ArrayList<>();
		for (Actor actor : listActor) {
			CustomActor customActor = new CustomActor(actor.getIdActor(), actor.getNameActor());
			listCustomActor.add(customActor);
		}
		return listCustomActor;
	}

}
