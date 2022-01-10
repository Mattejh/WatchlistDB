# WatchlistDB
School project for database theory course 2dv513

Simple JavaFX application to display information about movies, rate them and add movies to personal watchlist.
The movie data is gathered from this [Kaggle dataset](https://www.kaggle.com/tmdb/tmdb-movie-metadata?select=tmdb_5000_movies.csv).

See `pom.xml` for all dependencies.
Written in java 11 SDK.

## Installation
- Create a local database from MoviesDB_dump.sql
- Edit `ConnectDB` to change database url, username and password.
- Build project with maven.
- Run `InsertFromCsv.java` to fill database with entries.
- Run `App.java` to start the JavaFX application.
