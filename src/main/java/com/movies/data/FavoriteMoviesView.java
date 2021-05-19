package com.movies.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class FavoriteMoviesView {

    @Id
    private String movieId;
    private String original_title;

    public FavoriteMoviesView(String original_title){

        this.movieId = UUID.randomUUID().toString();
        this.original_title = original_title;
    }
    
}
