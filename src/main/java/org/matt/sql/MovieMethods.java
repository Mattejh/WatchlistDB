package org.matt.sql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.matt.dataBeans.CastData;
import org.matt.dataBeans.MovieData;
import org.matt.dataBeans.GenreData;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.matt.controllers.GlobalProperties.*;
import static org.matt.sql.ConnectDB.getConnection;

public class MovieMethods {

    public static ObservableList<MovieData> createInitData() {
        ObservableList<MovieData> data = FXCollections.observableArrayList();
        Connection conn = getConnection();
        PreparedStatement stmt;
        assert conn != null;
        try {
            stmt = conn.prepareStatement("select * from Movies");
            ResultSet rs;
            rs = stmt.executeQuery();

            while (rs.next()) {
                MovieData movie = new MovieData(rs.getInt("id"), rs.getString("title"),
                        rs.getString("overview"), rs.getDouble("vote_avg"),
                        rs.getInt("vote_count"), rs.getInt("runtime"),
                        rs.getDate("release_date"), rs.getString("url"));
                data.add(movie);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MovieData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<MovieData> getMoviesByGenre(String genre) {
        ObservableList<MovieData> data = FXCollections.observableArrayList();
        Connection conn = getConnection();
        PreparedStatement stmt;
        assert conn != null;
        try {
            stmt = conn.prepareStatement("select * from Movies join Movie_genre Mg on Movies.id = Mg.movie_id" +
                    " join Genre G on G.genre_id = Mg.genre_id where genre = ?");
            stmt.setString(1, genre);
            ResultSet rs;
            rs = stmt.executeQuery();

            while (rs.next()) {
                MovieData movie = new MovieData(rs.getInt("id"), rs.getString("title"),
                        rs.getString("overview"), rs.getDouble("vote_avg"),
                        rs.getInt("vote_count"), rs.getInt("runtime"),
                        rs.getDate("release_date"), rs.getString("url"));
                data.add(movie);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MovieData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

        return data;
    }

    public static ObservableList<String> getGenreData() {
        Connection conn = getConnection();
        PreparedStatement stmt;
        ObservableList<String> data = FXCollections.observableArrayList();
        String query = "select genre\n" +
                "from Movies\n" +
                "join Movie_genre Mg on Movies.id = Mg.movie_id\n" +
                "join Genre G on G.genre_id = Mg.genre_id\n" +
                "where Movies.id = ?";

        try {
            assert conn != null;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, getCurrentMovieId());
            ResultSet rs;
            rs = stmt.executeQuery();

            while (rs.next()) {
                data.add(rs.getString("genre"));
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(GenreData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<CastData> getCastViewData() {
        Connection conn = getConnection();
        PreparedStatement stmt;
        ObservableList<CastData> data = FXCollections.observableArrayList();
        String query = "select actor, role_character, sex from castView where movie_id=?";
        
        try {
            assert conn != null;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, getCurrentMovieId());
            ResultSet rs;
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                CastData row = new CastData(rs.getString("actor"),
                        rs.getString("role_character"), rs.getString("sex"));
                data.add(row);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(CastData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return data;
    }

    public static MovieData getMovieData() {
        Connection conn = getConnection();
        PreparedStatement stmt;
        MovieData data = null;
        String query = "select * from MoviesDB.Movies where id = ?";

        try {
            assert conn != null;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, getCurrentMovieId());
            ResultSet rs;
            rs = stmt.executeQuery();

            while (rs.next()) {
                data = new MovieData(rs.getInt("id"), rs.getString("title"),
                        rs.getString("overview"), rs.getDouble("vote_avg"),
                        rs.getInt("vote_count"), rs.getInt("runtime"),
                        rs.getDate("release_date"), rs.getString("url"));

            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(GenreData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return data;
    }


}
