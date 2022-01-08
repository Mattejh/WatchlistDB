package org.matt.scenes;

public enum Views {
    LOGIN {
        @Override
        String getFxml() {
            return "logIn.fxml";
        }
    },

    REGISTER {
        @Override
        String getFxml() {
            return "register.fxml";
        }
    },

    MAIN {
        @Override
        String getFxml() {
            return "main.fxml";
        }
    },

    DETAILS {
        @Override
        String getFxml() {
            return "moreDetails.fxml";
        }
    },

    WATCHLIST {
        @Override
        String getFxml() {
            return "watchlist.fxml";
        }
    },

    ABOUT {
        @Override
        String getFxml() {
            return "about.fxml";
        }
    };

    abstract String getFxml();
}
