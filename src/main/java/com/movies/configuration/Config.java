package com.movies.configuration;


import com.movies.data.FavoriteMoviesRepo;
import com.movies.service.MovieService;
import com.movies.service.MovieServiceImp;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@Configuration
@EnableJpaRepositories("com.movies.data")
public class Config {

    @Bean
    public MovieService movieService(FavoriteMoviesRepo favoriteMoviesRepo) {
        return new MovieServiceImp(favoriteMoviesRepo);
    }
}
