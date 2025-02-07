package com.example.chat;

import com.example.chat.Entity.User;
import com.example.chat.Repository.UserRepository;
import com.example.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatApplication implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = User.builder()
				.name("User 1")
				.username("user1")
				.password("admin123")
				.build();

		User user2 = User.builder()
				.name("User 2")
				.username("user2")
				.password("admin123")
				.build();

		userRepository.save(user1);
		userRepository.save(user2);
	}
}
