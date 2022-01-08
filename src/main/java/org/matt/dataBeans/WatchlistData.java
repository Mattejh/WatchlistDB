package org.matt.dataBeans;

import javafx.beans.property.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class WatchlistData {
    private final IntegerProperty user_id;
    private final IntegerProperty movie_id;
    private final StringProperty title;
    private final IntegerProperty runtime;
    private final ObjectProperty<LocalDate> releaseDate = new SimpleObjectProperty<>(this, "releaseDate");
    private final ObjectProperty<LocalDateTime> timestamp = new SimpleObjectProperty<>(this, "timestamp");

    public WatchlistData(int user_id, int movie_id, String title, int runtime, Date releaseDate, Timestamp timestamp) {
        this.user_id = new SimpleIntegerProperty(user_id);
        this.movie_id = new SimpleIntegerProperty(movie_id);
        this.title = new SimpleStringProperty(title);
        this.runtime = new SimpleIntegerProperty(runtime);
        setReleaseDate(releaseDate);
        setTimestamp(timestamp);
    }

    public int getUser_id() {
        return user_id.get();
    }

    public IntegerProperty user_idProperty() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id.set(user_id);
    }

    public int getMovie_id() {
        return movie_id.get();
    }

    public IntegerProperty movie_idProperty() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id.set(movie_id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getRuntime() {
        return runtime.get();
    }

    public IntegerProperty runtimeProperty() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime.set(runtime);
    }

    public LocalDate getReleaseDate() {
        return releaseDate.get();
    }

    public ObjectProperty<LocalDate> releaseDateProperty() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate.set(releaseDate.toLocalDate());
    }

    public LocalDateTime getTimestamp() {
        return timestamp.get();
    }

    public ObjectProperty<LocalDateTime> timestampProperty() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp.set(timestamp.toLocalDateTime());
    }
}
