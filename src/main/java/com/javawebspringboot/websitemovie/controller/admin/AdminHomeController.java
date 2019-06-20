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

import com.javawebspringboot.websitemovie.model.Movie;
import com.javawebspringboot.websitemovie.service.CategoryService;
import com.javawebspringboot.websitemovie.service.CountryService;
import com.javawebspringboot.websitemovie.service.MovieService;
import com.javawebspringboot.websitemovie.service.SlideService;

@Controller
public class AdminHomeController {

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
		return "admin/listFilm";
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
	
		movieService.saveMovie(movie, countryId,listCategory);
		return "redirect:/admin/them-phim-moi";
	}

	@RequestMapping("/admin/delete/{idMovie}")
	public String deleteMovie(@PathVariable(name = "idMovie") Integer idMovie) {
		movieService.deleteMovie(idMovie);
		return "redirect:/admin/danh-sach-phim";
	}
}
