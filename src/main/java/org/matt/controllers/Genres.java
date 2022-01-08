package org.matt.controllers;

public enum Genres {
    ADVENTURE("Adventure"),
    FANTASY("Fantasy"),
    ANIMATION("Animation"),
    DRAMA("Drama"),
    HORROR("Horror"),
    ACTION("Action"),
    COMEDY("Comedy"),
    HISTORY("History"),
    WESTERN("Western"),
    THRILLER("Thriller"),
    CRIME("Crime"),
    DOCUMENTARY("Documentary"),
    SCIENCE_FICTION("Science Fiction"),
    MYSTERY("Mystery"),
    MUSIC("Music"),
    ROMANCE("Romance"),
    FAMILY("Family"),
    WAR("War"),
    FOREIGN("Foreign"),
    TV_MOVIE("TV Movie");

    final String genre;

    Genres(String genre) {
    this.genre = genre;
    }

    @Override
    public String toString() {
        return genre;
    }
}
