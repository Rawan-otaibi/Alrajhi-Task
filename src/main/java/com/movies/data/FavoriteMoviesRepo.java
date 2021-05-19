package com.movies.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteMoviesRepo extends JpaRepository<FavoriteMoviesView, String> {

}
