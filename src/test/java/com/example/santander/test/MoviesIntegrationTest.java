package com.example.santander.test;

import com.example.santander.test.model.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MoviesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldAddMovie_whenPostMethodIsUsed() throws Exception {
        // Given
        Movie inputMovie = new Movie();
        inputMovie.setName("Movie name");

        // When
        final MvcResult mvcPostResult = mockMvc.perform(post("/movies")
                .content(asJsonString(inputMovie))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        String jsonPostMovie = mvcPostResult.getResponse().getContentAsString();
        Movie resultPostMovie = objectMapper.readValue(jsonPostMovie, Movie.class);
        assertThat(resultPostMovie.getName()).isEqualTo(inputMovie.getName());

        // Use get endpoint to verify the movie is there
        final MvcResult mvcGetResult = mockMvc.perform(get("/movies/"+resultPostMovie.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String jsonGetMovie = mvcGetResult.getResponse().getContentAsString();
        Movie resultGetMovie = objectMapper.readValue(jsonGetMovie, Movie.class);
        assertThat(resultGetMovie).isEqualTo(resultGetMovie);
    }

    private String asJsonString(Movie inputMovie) {
        try {
            return objectMapper.writeValueAsString(inputMovie);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}