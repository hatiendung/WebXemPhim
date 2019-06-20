package com.javawebspringboot.websitemovie.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javawebspringboot.websitemovie.service.SlideService;

@Controller
@ResponseBody
public class AjaxAPI {
	
	@Autowired
	private SlideService slideService;

	@RequestMapping(value = "/api/ajax/idSlide/{idSlide}", method = RequestMethod.GET)
	public void ajax(@PathVariable(name = "idSlide") Integer idSlide) {
		
		slideService.updateSlideByStatus(idSlide);

	}

}
