package com.instagram.instagram;

import com.github.javafaker.Faker;
import com.instagram.instagram.models.Comment;
import com.instagram.instagram.models.Post;
import com.instagram.instagram.models.User;
import com.instagram.instagram.repo.PostRepository;
import com.instagram.instagram.repo.UserRepository;
import org.hibernate.type.LocalDateType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@SpringBootApplication
public class InstagramApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstagramApplication.class, args);
	}

	//TODO: user_simple_view not counting properly
	//TODO: Replace DTOs with SimpleView (maybe?)

}
