package com.example.santander.test.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String name;

    private int year;

    private int epgRating;
}
