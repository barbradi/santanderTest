package com.example.santander.test.service;

import com.example.santander.test.model.Movie;
import com.example.santander.test.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Test
    void getMovie() {
        // Given
        long id = 1L;
        Movie expectedMovie = new Movie();
        expectedMovie.setId(id);
        expectedMovie.setName("movie name");
        when(movieRepository.findById(1L)).thenReturn(Optional.of(expectedMovie));

        // When
        Movie movie = movieService.getMovie(id);

        // Then
        assertThat(movie).isEqualTo(expectedMovie);
    }

    @Test
    void addMovie() {
        // Given
        long id = 1L;
        Movie inputMovie = new Movie();
        inputMovie.setName("movie name");
        Movie createdMovie = new Movie();
        createdMovie.setId(id);
        createdMovie.setName("movie name");
        when(movieRepository.save(inputMovie)).thenReturn(createdMovie);

        // When
        Movie movie = movieService.addMovie(inputMovie);

        // Then
        assertThat(movie).isEqualTo(createdMovie);
    }

    @Test
    void deleteMovie() {
        long id = 1L;

        // When
        movieService.deleteMovie(id);

        // Then
        verify(movieRepository).deleteById(id);

    }
}