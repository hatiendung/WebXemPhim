package com.javawebspringboot.websitemovie.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javawebspringboot.websitemovie.model.Country;
import com.javawebspringboot.websitemovie.model.EpisodeSeries;
import com.javawebspringboot.websitemovie.model.Movie;
import com.javawebspringboot.websitemovie.repository.CountryRepository;
import com.javawebspringboot.websitemovie.service.CategoryService;
import com.javawebspringboot.websitemovie.service.CountryService;
import com.javawebspringboot.websitemovie.service.EpisodeSeriesService;
import com.javawebspringboot.websitemovie.service.MovieService;
import com.javawebspringboot.websitemovie.service.SlideService;

@Controller
public class AdminHomeController {

	@Autowired
	private EpisodeSeriesService episodeSeriesService;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private SlideService slideService;

	@Autowired
	private MovieService movieService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/admin")
	public String showHomePage(Model model) {

		// UserDetails userDetails = (UserDetails)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return "admin/home";
	}

	@RequestMapping("/admin/danh-sach-phim")
	public String showListMovie(Model model) {
		model.addAttribute("listMovie", movieService.findAllMovie());
		return "admin/listMovie";
	}

	@RequestMapping("/admin/danh-sach-slide")
	public String showListSlide(Model model) {
		model.addAttribute("listSlide", slideService.findAllSlide());
		return "admin/listSlide";
	}

	@RequestMapping("/admin/them-phim-moi")
	public String addNewMovie(Model model) {

		model.addAttribute("movie", new Movie());
		model.addAttribute("countryList", countryService.findAllCountry());
		model.addAttribute("categorys", categoryService.findAllCategory());

		return "admin/addNewMovie";
	}

	@RequestMapping(value = "/admin/add-new-film", method = RequestMethod.POST)
	public String newMovie(@ModelAttribute("movie") Movie movie,
			@RequestParam(required = true, name = "countryId") Integer countryId,
			@RequestParam(name = "category", required = false, defaultValue = "0") List<Integer> listCategory) {

		movieService.saveMovie(movie, countryId, listCategory);
		return "redirect:/admin/them-phim-moi";
	}

	@RequestMapping("/admin/delete/{idMovie}")
	public String deleteMovie(@PathVariable(name = "idMovie") Integer idMovie) {
		movieService.deleteMovie(idMovie);
		return "redirect:/admin/danh-sach-phim";
	}

	@RequestMapping("/admin/xoa-slide/{idSlide}")
	public String deleteSlide(@PathVariable(name = "idSlide") Integer idSlide) {
		slideService.deleteSlideByIdSlide(idSlide);
		return "redirect:/admin/danh-sach-slide";
	}

	@RequestMapping("/admin/country/{idCountry}")
	public String deleteCountry(@PathVariable(name = "idCountry") Integer idCountry) {
		Country country = countryRepository.findByIdCountry(idCountry);
		List<Movie> movieList = movieService.findByCountry(country);
		if (movieList != null) {
			// thong ba

		} else {
			// xoa binh thuong
		}

		return "";
	}

	@RequestMapping("/admin/phim/{linkMovie}")
	public String movieDescriptionEpisode(Model model, @PathVariable(name = "linkMovie") String linkMovie) {

		Movie movie = movieService.findByLinkMovie(linkMovie);
		if (movie.getEpisodeSeriesList().size() > 0) {
			// co tap phim
			return "redirect:/admin/xem-phim/" + movie.getEpisodeSeriesList().get(0).getLinkEpisode();
		}
		model.addAttribute("movie", movie);
		model.addAttribute("episode", null);

		return "admin/play_descriptionMovie";
	}

	@RequestMapping("/admin/xem-phim/{linkEpisode}")
	public String playMovie(Model model, @PathVariable(name = "linkEpisode") String linkEpisode) {
		EpisodeSeries episode = episodeSeriesService.findByLinkEpisode(linkEpisode);

		// xem 1 bo phim
		Movie movie = episode.getMovie();
		model.addAttribute("movie", movie);
		model.addAttribute("episode", episode);
		return "admin/play_descriptionMovie";
	}

	@RequestMapping(value = "/admin/phim/{linkMovie}/them-tap-phim-moi", method = RequestMethod.POST)
	@ResponseBody
	public String addEpisode(@RequestParam(name = "videoEpisode") MultipartFile multipartFile,
			@PathVariable(name = "linkMovie") String linkMovie) {
		episodeSeriesService.addNewEpisode(linkMovie, multipartFile);
		return "redirect:/admin/phim{linkMovie}";
	}

}
