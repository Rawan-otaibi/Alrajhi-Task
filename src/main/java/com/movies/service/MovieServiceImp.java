package com.movies.service;


import com.movies.data.FavoriteMoviesRepo;
import com.movies.data.FavoriteMoviesView;
import com.movies.exception.PageLessThanOneException;
import com.movies.exception.NoRecordFoundException;
import com.movies.model.MovieModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@CacheConfig(cacheNames = "movies")
@RequiredArgsConstructor
public class MovieServiceImp implements MovieService {

    private final FavoriteMoviesRepo favoriteMoviesRepo;


    @Override
    public Object getLatestMovie() throws NoRecordFoundException {

        log.info("Type: service, Status: end, log-message: start retrieving latest movie");
        RestTemplate restTemplate = new RestTemplate();
        String url
                = "https://api.themoviedb.org/3/movie/latest?api_key=587de429cb179ffc141480b477c11d91&language=en-US";
        return restTemplate.getForEntity(url, Object.class);
    }

    @Override
    @Cacheable(key = "{#methodName, #page, #title}")
    public Object searchForMovieByTitle(int page, String title) throws PageLessThanOneException {

        log.info("Type: service, Status: start, log-message: Searching for a movie with title: {}", title);
        RestTemplate restTemplate = new RestTemplate();
        String url
                = "https://api.themoviedb.org/3/search/movie?api_key=587de429cb179ffc141480b477c11d91&language=en-US&page=" + page + "&include_adult=true&query=" + title;
        try {
            return restTemplate.getForEntity(url, Object.class);
        } catch (HttpClientErrorException.UnprocessableEntity e) {
            throw new PageLessThanOneException("Page number should be greater than 0");
        }
    }

    @Override
    @CacheEvict(cacheNames = "movies", allEntries = true)
    public void addMovieToFavorite(String original_title) {

        log.info("Type: service, Status: start, log-message: Start adding the movie to favorite");
        this.favoriteMoviesRepo.save(new FavoriteMoviesView(original_title));
        log.info("Type: service, Status: start, log-message: The movie added to favorite");
    }

    @Override
    @Cacheable(key = "{#methodName}")
    public List<MovieModel> getAllMyFavoritesMovies() throws NoRecordFoundException {

        log.info("Type: service, Status: start, log-message: Start retrieving favorites movies");
        List<FavoriteMoviesView> views = this.favoriteMoviesRepo.findAll();
        if (views != null && !views.isEmpty()) {
            return views.stream().map(
                    favoriteMoviesView -> new MovieModel(favoriteMoviesView.getMovieId(), favoriteMoviesView.getOriginal_title()
                    )).collect(Collectors.toList());
        } else {
            log.info("Type: service, Status: end with exception, log-message: No records found");
            throw new NoRecordFoundException("No records found");
        }
    }

    @Override
    @CacheEvict(cacheNames = "movies", allEntries = true)
    public void removeMovieFromFavorite(String id) throws NoRecordFoundException {

        log.info("Type: service, Status: start, log-message: start removing movie from favorite");
        try {
            this.favoriteMoviesRepo.deleteById(id);
        } catch (NullPointerException | EmptyResultDataAccessException e){
            log.info("Type: service, Status: end with exception, log-message: No record found for movie with id: {}", id);
            throw new NoRecordFoundException();
        }
    }

    @Override
    @Cacheable(key = "{#methodName, #imdb_id}")
    public Object getMovieDetails(String imdb_id) {

        log.info("Type: service, Status: end, log-message: start retrieving details for the movie");
        RestTemplate restTemplate = new RestTemplate();
        String url
                = "https://api.themoviedb.org/3/movie/" + imdb_id + "?api_key=587de429cb179ffc141480b477c11d91&language=en-US";
        return restTemplate.getForEntity(url, Object.class);
    }

    @Override
    @Cacheable(key = "{#methodName, #imdb_id, #page}")
    public Object getSimilarPages(String imdb_id, int page) throws PageLessThanOneException {

        log.info("Type: service, Status: end, log-message: start retrieving similar movies");
        RestTemplate restTemplate = new RestTemplate();
        String url
                = "https://api.themoviedb.org/3/movie/" + imdb_id + "/similar?api_key=587de429cb179ffc141480b477c11d91&language=en-US&page=" + page;
        try {
            return restTemplate.getForEntity(url, Object.class);
        } catch (HttpClientErrorException.UnprocessableEntity | HttpClientErrorException.BadRequest e) {
            throw new PageLessThanOneException("Page number should be greater than 0");
        }
    }
}
