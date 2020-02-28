package com.example.santander.test.controller;

import com.example.santander.test.model.Movie;
import com.example.santander.test.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MoviesController.class)
class MoviesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieService movieService;

    @Test
    public void getMovie() throws Exception {
        // Given
        Movie expectedMovie = new Movie();
        expectedMovie.setName("movie name");
        when(movieService.getMovie(1)).thenReturn(expectedMovie);

        // When
        final MvcResult mvcResult = mockMvc.perform(get("/movies/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String jsonMovie = mvcResult.getResponse().getContentAsString();
        Movie movie = objectMapper.readValue(jsonMovie, Movie.class);
        assertThat(movie.getName()).isEqualTo(expectedMovie.getName());
    }

    @Test
    public void addMovie() throws Exception {
        // Given
        Movie inputMovie = new Movie();
        inputMovie.setName("resultMovie name");
        Movie createdMovie = new Movie();
        createdMovie.setName(inputMovie.getName());
        createdMovie.setId(1);
        when(movieService.addMovie(inputMovie)).thenReturn(createdMovie);

        // When
        final MvcResult mvcResult = mockMvc.perform(post("/movies")
                .content(asJsonString(inputMovie))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonMovie = mvcResult.getResponse().getContentAsString();
        Movie resultMovie = objectMapper.readValue(jsonMovie, Movie.class);
        assertThat(resultMovie.getName()).isEqualTo(inputMovie.getName());
        assertThat(resultMovie.getId()).isEqualTo(createdMovie.getId());

    }

    @Test
    public void deleteMovie() throws Exception {
        // Given
        long id = 1;

        // When
        final MvcResult mvcResult = mockMvc.perform(delete("/movies/" + id))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        // Then
        verify(movieService).deleteMovie(id);

    }

    private String asJsonString(Movie inputMovie) {
        try {
            return objectMapper.writeValueAsString(inputMovie);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}