package org.matt.dataBeans;

import javafx.beans.property.*;

public class UserData {
    private IntegerProperty user_id = new SimpleIntegerProperty();
    private DoubleProperty rating = new SimpleDoubleProperty();
    private StringProperty name;
    private StringProperty password;

    public UserData() {
    }

    public UserData(String name, String password) {
        this.name = new SimpleStringProperty(name);
        this.password = new SimpleStringProperty(password);
    }

    public double getRating() {
        return rating.get();
    }

    public DoubleProperty ratingProperty() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating.set(rating);
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

}
