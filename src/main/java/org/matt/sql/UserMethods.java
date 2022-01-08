package org.matt.sql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.matt.controllers.GlobalProperties;
import org.matt.dataBeans.CastData;
import org.matt.dataBeans.MovieData;
import org.matt.dataBeans.UserData;
import org.matt.dataBeans.WatchlistData;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.matt.controllers.GlobalProperties.getCurrentMovieId;
import static org.matt.controllers.GlobalProperties.getCurrentUserId;
import static org.matt.sql.ConnectDB.getConnection;

public class UserMethods {

    public static boolean authenticateUser(UserData user) {
        Connection conn = ConnectDB.getConnection();
        int count = 0;
        try {
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement("select distinct name from User where name=? and password=?");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                count++;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    public static void addUser(UserData user) {
        Connection conn = ConnectDB.getConnection();

        try {
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement("insert into User (name, password) values (?,?)");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.execute();
            conn.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText(user.getName() + " successfully registered, you may now log in");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Credentials");
            alert.setHeaderText(null);
            alert.setContentText("Username: " + user.getName() + " is already in use, try another");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public static void addId(UserData user) {
        Connection conn = ConnectDB.getConnection();

        try {
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement("select distinct user_id from User where name=? and password=?");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());

            ResultSet rs = stmt.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = (rs.getInt("user_id"));
            }
            user.setUser_id(id);
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public static UserData getUser() {
        Connection conn = ConnectDB.getConnection();
        PreparedStatement stmt;
        UserData user = null;

        try {
            assert conn != null;
            stmt = conn.prepareStatement("select * from User where user_id=?");
            stmt.setInt(1, GlobalProperties.getCurrentUserId());
            ResultSet rs;

            rs = stmt.executeQuery();

            while (rs.next()) {
                user = new UserData(rs.getString("name"), rs.getString("password"));
            }
            assert user != null;
            user.setUser_id(GlobalProperties.getCurrentUserId());
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return user;
    }

    public static Double getUserMovieRating() {
        Connection conn = ConnectDB.getConnection();
        double rating = 0.0;

        try {
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement("select distinct (rating) from User_ratings where user_id=? and movie_id=?");
            stmt.setInt(1, GlobalProperties.getCurrentUserId());
            stmt.setInt(2, GlobalProperties.getCurrentMovieId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                rating = rs.getDouble("rating");
            }
            conn.close();

        } catch (SQLException e) {
            Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return rating;
    }

    public static boolean hasMovieRating() {
        Connection conn = ConnectDB.getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement("select * from User_ratings where movie_id = ? and user_id = ?");
            stmt.setInt(1, GlobalProperties.getCurrentMovieId());
            stmt.setInt(2, GlobalProperties.getCurrentUserId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return false;
    }

    public static void addUserMovieRating(double rating) {
        Connection conn = ConnectDB.getConnection();
        try {
            PreparedStatement stmt1 = conn.prepareStatement("insert ignore into User_ratings (movie_id) (select id from Movies where id=?)");
            stmt1.setInt(1, GlobalProperties.getCurrentMovieId());
            stmt1.execute();

            PreparedStatement stmt2 = conn.prepareStatement("update User_ratings set rating=?, user_id=? where movie_id=?");
            stmt2.setDouble(1, rating);
            stmt2.setInt(2, GlobalProperties.getCurrentUserId());
            stmt2.setInt(3, GlobalProperties.getCurrentMovieId());
            stmt2.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public static ObservableList<WatchlistData> getUserWatchlist() {
        Connection conn = getConnection();
        PreparedStatement stmt;
        ObservableList<WatchlistData> data = FXCollections.observableArrayList();
        String query = "select * from watchlistView where user_id=?";

        try {
            assert conn != null;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, getCurrentUserId());
            ResultSet rs;
            rs = stmt.executeQuery();

            while (rs.next()) {
                WatchlistData row = new WatchlistData(rs.getInt("user_id"), rs.getInt("movie_id"),
                        rs.getString("title"), rs.getInt("runtime"),
                        rs.getDate("release_date"), rs.getTimestamp("timestamp"));
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

    public static int countWatchlist() {
        Connection conn = getConnection();
        PreparedStatement stmt;
        int count = 0;
        String query = "select count(*) from Watchlist where user_id=?";

        try {
            assert conn != null;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, getCurrentUserId());
            ResultSet rs;
            rs = stmt.executeQuery();

            while (rs.next()) {
                count = rs.getInt(1);
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            Logger.getLogger(CastData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return count;

    }

    public static void addMovieToWatchlist() {
        Connection conn = getConnection();
        PreparedStatement stmt;
        String query = "insert ignore into Watchlist (user_id, movie_id, timestamp) VALUES (?,?,?)";

        try {
            assert conn != null;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, getCurrentUserId());
            stmt.setInt(2, getCurrentMovieId());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(WatchlistData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public static void deleteMovieFromWatchlist() {
        Connection conn = getConnection();
        PreparedStatement stmt;
        String query = "delete from Watchlist where movie_id =? and user_id=?";

        try {
            assert conn != null;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, getCurrentMovieId());
            stmt.setInt(2, getCurrentUserId());

            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(WatchlistData.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public static void updateMovieRating(double rating) {
        if (!UserMethods.hasMovieRating()) {
            Connection conn = getConnection();
            PreparedStatement stmt;
            String query = "update Movies\n" +
                    "set vote_avg = vote_avg + ((? + vote_avg) / (vote_count + 1)),\n" +
                    " vote_count = vote_count + 1\n" +
                    "where id = ?";
            try {
                assert conn != null;
                stmt = conn.prepareStatement(query);
                stmt.setDouble(1, rating);
                stmt.setInt(2, getCurrentMovieId());
                stmt.executeUpdate();

                stmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(MovieData.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("User already voted");
            alert.showAndWait();
        }
    }
}
