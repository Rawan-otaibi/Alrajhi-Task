package com.movies.controller;

import com.movies.controller.dto.MovieDto;
import com.movies.controller.dto.MovieRequest;
import com.movies.exception.NoRecordFoundException;
import com.movies.exception.PageLessThanOneException;
import com.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@RestController
@RequestMapping(value = "movies")
@RequiredArgsConstructor
public class MovieRestController {

    private final MovieService movieService;


    @GetMapping(value = "/latest")
    public Object getLatestMovie() throws NoRecordFoundException {

        log.info("Type: rest, Status: start, log-message: start retrieving latest movie");
        return this.movieService.getLatestMovie();
    }

    @GetMapping(value = "search/title/{title}/pages/{page}")
    public Object searchForMovieByTitle(@PathVariable String title, @PathVariable int page) throws PageLessThanOneException {

        log.info("Type: rest, Status: start, log-message: start searching for movie");
        return this.movieService.searchForMovieByTitle(page, title);
    }

    @PostMapping
    public void addMovieToFavorite(@RequestBody MovieRequest movieRequest) {

        log.info("Type: rest, Status: start, log-message: start adding the movie to favorite");
        this.movieService.addMovieToFavorite(movieRequest.getOriginal_title());
    }   

    @GetMapping(value = "favorites")
    public List<MovieDto> getAllMyFavoritesMovies() throws NoRecordFoundException {

        log.info("Type: rest, Status: start, log-message: start retrieving favorites movies");
        return this.movieService.getAllMyFavoritesMovies().stream().map(movieModel -> new MovieDto(movieModel.getMovieId(),
                movieModel.getOriginal_title())).collect(Collectors.toList());
    }

    @DeleteMapping(value = "id/{id}")
    public void removeMovieFromFavorite(@PathVariable String id) throws NoRecordFoundException {

        log.info("Type: rest, Status: start, log-message: start deleting movie from favorite");
        this.movieService.removeMovieFromFavorite(id);
    }

    @GetMapping(value = "details/imdb_id/{imdb_id}")
    public Object getMovieDetails(@PathVariable String imdb_id) {

        log.info("Type: rest, Status: start, log-message: start getting details of the movie");
        return this.movieService.getMovieDetails(imdb_id);
    }

    @GetMapping(value = "/imdb_id/{imdb_id}/similar/pages/{page}")
    public Object getSimilarMovies(@PathVariable String imdb_id, @PathVariable int page) throws PageLessThanOneException {

        log.info("Type: rest, Status: start, log-message: start searching for similar movies");
        return this.movieService.getSimilarPages(imdb_id, page);
    }
}
