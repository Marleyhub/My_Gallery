package com.github.marleyhub.my_gallery.mapper;

import java.util.List;
import java.util.Optional;

import com.github.marleyhub.my_gallery.dto.UserDto;
import com.github.marleyhub.my_gallery.entities.User;

public class UserMapper {

	public static UserDto toDto(User user) {
		return new UserDto(user.getId(), user.getEmail(), user.getPassword());
	}
	
	public static List<UserDto> toDto(List<User> users){
		if (users == null) {
			return List.of();
		}
		return users.stream()
					.map(UserMapper::toDto)
					.toList();
	}
	
	public static Optional<UserDto> toDto(Optional<User> user){
		return user.map(UserMapper::toDto);
	}
	
	public static User toEntity(UserDto userDto) {
		return new User(userDto.getId(), userDto.getEmail(), userDto.getPassword());
	}
}
