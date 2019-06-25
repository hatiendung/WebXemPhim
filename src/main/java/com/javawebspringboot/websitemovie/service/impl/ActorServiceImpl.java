package com.javawebspringboot.websitemovie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javawebspringboot.websitemovie.model.Actor;
import com.javawebspringboot.websitemovie.repository.ActorRepository;
import com.javawebspringboot.websitemovie.service.ActorService;

@Service
@Transactional
public class ActorServiceImpl implements ActorService {

	@Autowired
	private ActorRepository actorRepository;

	@Override
	public Actor findActorByCodeActor(String codeActor) {
		return actorRepository.findActorByCodeActor(codeActor);
	}

	@Override
	public List<Actor> searchActor(String nameActor) {

		return actorRepository.searchActor(nameActor);
	}

}
