package com.javawebspringboot.websitemovie.service;

import java.util.List;

import com.javawebspringboot.websitemovie.model.Slide;

public interface SlideService {

	List<Slide> findAllSlide();

	void updateSlideByStatus(Integer idSlide);

	List<Slide> findAllSlideByStatus1();

}