package com.javawebspringboot.websitemovie.service;

import com.javawebspringboot.websitemovie.model.User;

public interface UserService {
	
	User findByEmail(String email);

}
