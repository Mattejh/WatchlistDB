package org.matt.dataBeans;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CastData {
    private StringProperty actor;
    private StringProperty character;
    private StringProperty gender;

    public CastData(String actor, String character, String gender) {
        this.actor = new SimpleStringProperty(actor);
        this.character = new SimpleStringProperty(character);
        this.gender = new SimpleStringProperty(gender);
    }

    public String getActor() {
        return actor.get();
    }

    public StringProperty actorProperty() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor.set(actor);
    }

    public String getCharacter() {
        return character.get();
    }

    public StringProperty characterProperty() {
        return character;
    }

    public void setCharacter(String character) {
        this.character.set(character);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }
}
