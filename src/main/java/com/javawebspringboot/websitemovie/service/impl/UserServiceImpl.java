package com.javawebspringboot.websitemovie.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javawebspringboot.websitemovie.model.User;
import com.javawebspringboot.websitemovie.repository.UserRepository;
import com.javawebspringboot.websitemovie.service.UserService;

@Service
@Transactional
public class UserServiceImpl  implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

}
