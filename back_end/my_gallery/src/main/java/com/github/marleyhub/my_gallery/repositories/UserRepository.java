package com.github.marleyhub.my_gallery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.marleyhub.my_gallery.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}