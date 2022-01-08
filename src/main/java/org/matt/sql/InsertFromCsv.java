package org.matt.sql;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import org.matt.dataBeans.MovieData;

import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InsertFromCsv {

    public static void moviesToDb() {
        String sql_genre = "insert ignore into Genre (genre_id, genre) VALUES (?, ?)";
        String sql_movieGenre = "insert ignore into Movie_genre (movie_id, genre_id) values (?, ?)";
        String sql_movies = "insert ignore into Movies (id, title, overview, vote_avg, vote_count, runtime, release_date, url) values (?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement genre_stmt = null;
        PreparedStatement movieGenre_stmt = null;
        PreparedStatement movies_stmt = null;
        boolean autoCommit = false;
        BufferedReader br;
        int batchLimit = 1000;

        try {
            conn = ConnectDB.getConnection();
            genre_stmt = conn.prepareStatement(sql_genre);
            movieGenre_stmt = conn.prepareStatement(sql_movieGenre);
            movies_stmt = conn.prepareStatement(sql_movies);
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            br = new BufferedReader(new FileReader("src/main/resources/org/matt/tmdb_5000_movies_copy.csv"));
            CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();
            CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();
            String[] line;

            while ((line = csvReader.readNext()) != null) {
                String genreJson = line[1];
                JsonReader reader = new JsonReader(new StringReader(genreJson));
                JsonArray dataJson = JsonParser.parseReader(reader).getAsJsonArray();
                int id = Integer.parseInt(line[3]);
                String title = line[6];
                String overview = line[7];
                double vote_avg = Double.parseDouble(line[18]);
                int vote_count = Integer.parseInt(line[19]);
                int runtime = Integer.parseInt(line[13]);
                Date releaseDate = Date.valueOf(LocalDate.parse(line[11]));
                String homePage = line[2];

                movies_stmt.setInt(1, id);
                movies_stmt.setString(2, title);
                movies_stmt.setString(3, overview);
                movies_stmt.setDouble(4, vote_avg);
                movies_stmt.setInt(5, vote_count);
                movies_stmt.setInt(6, runtime);
                movies_stmt.setDate(7, releaseDate);
                movies_stmt.setString(8, homePage);
                movies_stmt.addBatch();
                batchLimit--;

                for (int i = 0; i < dataJson.size(); i++) {
                    JsonObject obj = dataJson.get(i).getAsJsonObject();
                    genre_stmt.setInt(1, obj.get("id").getAsInt());
                    genre_stmt.setString(2, obj.get("name").getAsString());
                    genre_stmt.addBatch();
                    batchLimit--;
                    movieGenre_stmt.setInt(1, id);
                    movieGenre_stmt.setInt(2, obj.get("id").getAsInt());
                    movieGenre_stmt.addBatch();
                    batchLimit--;
                }

                if (batchLimit == 0) {
                    genre_stmt.executeBatch();
                    movies_stmt.executeBatch();
                    movieGenre_stmt.executeBatch();
                    genre_stmt.clearBatch();
                    movies_stmt.clearBatch();
                    movieGenre_stmt.clearBatch();
                    batchLimit = 1000;
                }
                genre_stmt.clearParameters();
                movies_stmt.clearParameters();
                movieGenre_stmt.clearParameters();
            }
        } catch (SQLException | IOException | CsvValidationException e) {
            e.printStackTrace();
        } finally {
            try{
                genre_stmt.executeBatch();
                movies_stmt.executeBatch();
                movieGenre_stmt.executeBatch();
                conn.commit();
                conn.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    genre_stmt.clearBatch();
                    movies_stmt.clearBatch();
                    movieGenre_stmt.clearBatch();
                    movieGenre_stmt.close();
                    genre_stmt.close();
                    movies_stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void creditsToDb()  {
        Connection conn = null;
        PreparedStatement cast_stmt = null;
        PreparedStatement movieCast_stmt = null;
        BufferedReader br;
        boolean autoCommit = false;
        int batchLimit = 1000;

        try {
            conn = ConnectDB.getConnection();
            cast_stmt = conn.prepareStatement("insert ignore into MoviesDB.Cast (cast_id, movie_id, actor, role_character, sex, cast_order) VALUES (?,?,?,?,?,?)");
            movieCast_stmt = conn.prepareStatement("insert ignore into MoviesDB.Movie_production (movie_id, cast_id) values (?,?)");
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            br = new BufferedReader(new FileReader("src/main/resources/org/matt/tmdb_5000_credits_copy.csv"));
            RFC4180Parser parser = new RFC4180ParserBuilder().withSeparator('\t').build();
            CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();
            List<String[]> list;
            list = csvReader.readAll();
            br.close();
            csvReader.close();

            for (String[] strings : list) {
                int movie_id = Integer.parseInt(strings[0]);
                String castJson = strings[2];
                JsonReader reader = new JsonReader(new StringReader(castJson));
                JsonArray castList = JsonParser.parseReader(reader).getAsJsonArray();

                for (int j = 0; j < castList.size(); j++) {
                    JsonObject obj = castList.get(j).getAsJsonObject();
                    cast_stmt.setInt(1, obj.get("id").getAsInt());
                    cast_stmt.setInt(2, movie_id);
                    cast_stmt.setString(3, obj.get("name").getAsString());
                    cast_stmt.setString(4, obj.get("character").getAsString());
                    cast_stmt.setString(5, obj.get("gender").getAsInt() == 1 ? "Female" : "Male");
                    cast_stmt.setInt(6, obj.get("order").getAsInt());
                    cast_stmt.addBatch();
                    batchLimit--;
                    movieCast_stmt.setInt(1, movie_id);
                    movieCast_stmt.setInt(2, obj.get("id").getAsInt());
                    movieCast_stmt.addBatch();
                    batchLimit--;
                }
                if (batchLimit == 0) {
                    cast_stmt.executeBatch();
                    movieCast_stmt.executeBatch();
                    cast_stmt.clearBatch();
                    movieCast_stmt.clearBatch();
                    batchLimit = 1000;
                }
                cast_stmt.clearParameters();
                movieCast_stmt.clearParameters();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                cast_stmt.executeBatch();
                movieCast_stmt.executeBatch();
                conn.commit();
                conn.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    cast_stmt.clearBatch();
                    movieCast_stmt.clearBatch();
                    cast_stmt.close();
                    movieCast_stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
