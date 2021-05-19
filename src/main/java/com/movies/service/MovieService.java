package com.movies.service;


import com.movies.exception.PageLessThanOneException;
import com.movies.exception.NoRecordFoundException;
import com.movies.model.MovieModel;

import java.util.List;

public interface MovieService {

    Object getLatestMovie() throws NoRecordFoundException;

    Object searchForMovieByTitle(int page, String title) throws PageLessThanOneException;

    void addMovieToFavorite(String original_title);

    List<MovieModel> getAllMyFavoritesMovies() throws NoRecordFoundException;

    void removeMovieFromFavorite(String id) throws NoRecordFoundException;

    Object getMovieDetails(String imdb_id);

    Object getSimilarPages(String imdb_id, int page) throws PageLessThanOneException;
}
