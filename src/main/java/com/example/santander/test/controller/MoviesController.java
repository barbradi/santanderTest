package com.example.santander.test.controller;

import com.example.santander.test.model.Movie;
import com.example.santander.test.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies")
public class MoviesController {

    private MovieService movieService;

    public MoviesController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("{id}")
    public Movie getMovie(@PathVariable long id){
        return movieService.getMovie(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Movie addMovie(@RequestBody Movie movie){
        return movieService.addMovie(movie);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteMovie(@PathVariable long id){
        movieService.deleteMovie(id);
    }
}
