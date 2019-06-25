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
import org.springframework.web.multipart.MultipartFile;

import com.javawebspringboot.websitemovie.model.Episode;
import com.javawebspringboot.websitemovie.model.Movie;
import com.javawebspringboot.websitemovie.model.Slide;
import com.javawebspringboot.websitemovie.service.CategoryService;
import com.javawebspringboot.websitemovie.service.CountryService;
import com.javawebspringboot.websitemovie.service.EpisodeService;
import com.javawebspringboot.websitemovie.service.MovieService;
import com.javawebspringboot.websitemovie.service.SlideService;
import com.javawebspringboot.websitemovie.service.TrailerService;
import com.javawebspringboot.websitemovie.service.UserService;

@Controller
public class AdminHomeController {

	@Autowired
	private EpisodeService episodeService;

	@Autowired
	private SlideService slideService;

	@Autowired
	private MovieService movieService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private TrailerService trailerService;

	@RequestMapping("/admin")
	public String showHomePage(Model model) {
		model.addAttribute("totalMovie", movieService.countMovie());
		model.addAttribute("totalEpisode", episodeService.countEpisode());
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
		Episode episode = episodeService.findByLinkEpisode(linkEpisode);

		// xem 1 bo phim
		Movie movie = episode.getMovie();
		movieService.sortEpisode(movie);
		model.addAttribute("movie", movie);
		model.addAttribute("episode", episode);
		return "admin/play_descriptionMovie";
	}

	@RequestMapping(value = "/admin/phim/{linkMovie}/them-tap-phim-moi", method = RequestMethod.POST)
	public String addEpisode(@RequestParam(name = "videoEpisode") MultipartFile videoEpisode,
			@PathVariable(name = "linkMovie") String linkMovie) {
		episodeService.addNewEpisode(linkMovie, videoEpisode);
		return "redirect:/admin/phim/{linkMovie}";
	}

	@RequestMapping("/admin/danh-sach-user")
	public String listUser(Model model) {
		model.addAttribute("listUser", userService.findAllUser());
		return "admin/listUser";
	}

	@RequestMapping("/admin/update/{linkMovie}")
	public String updateMovie(@PathVariable(name = "linkMovie") String linkMovie, Model model) {
		Movie movieUpdate = movieService.findByLinkMovie(linkMovie);
		model.addAttribute("movieUpdate", movieUpdate);
		model.addAttribute("countryList", countryService.findAllCountry());
		model.addAttribute("categorys", categoryService.findAllCategory());
		model.addAttribute("categoryList", movieUpdate.getCategoryList());
		Slide slide = slideService.findByMovie(movieUpdate);
		String linkSlide = "";
		if (slide != null) {
			linkSlide = slide.getLinkImage();
		}
		model.addAttribute("linkSlide", linkSlide);
		return "admin/updateMovie";
	}

	@RequestMapping("/admin/delete-episode/{linkEpisode}")
	public String deleteEpisode(@PathVariable(name = "linkEpisode") String linkEpisode) {

		Episode episode = episodeService.findByLinkEpisode(linkEpisode);
		String linkMovie = episode.getMovie().getLinkMovie();

		episodeService.deleteEpisode(linkEpisode);

		return "redirect:/admin/phim/" + linkMovie;
	}

	@RequestMapping("/admin/trailer/{linkMovie}/them-trailer-phim")
	public String addNewTrailer(Model model, @PathVariable(name = "linkMovie") String linkMovie,
			@RequestParam(name = "videoTrailer") MultipartFile videoTrailer) {
		trailerService.addNewTrailer(linkMovie, videoTrailer);
		return "redirect:/admin/phim/{linkMovie}";
	}

	@RequestMapping("/admin/tim-kiem-phim/")
	public String searchMovie(Model model, @RequestParam(name = "keyWord") String keyWord) {
		List<Movie> movieList = movieService.searchMovie(keyWord);
		model.addAttribute("movieList", movieList);
		return "admin/searchMovie";
	}

}
