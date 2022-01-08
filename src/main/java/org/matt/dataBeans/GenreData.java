package org.matt.dataBeans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GenreData {
    private IntegerProperty genre_id;
    private StringProperty  genre;

    public GenreData(int genre_id, String genre) {
        this.genre_id = new SimpleIntegerProperty(genre_id);
        this.genre = new SimpleStringProperty(genre);
    }

    public int getGenre_id() {
        return genre_id.get();
    }

    public IntegerProperty genre_idProperty() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id.set(genre_id);
    }

    public String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }
}
