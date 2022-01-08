package org.matt.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.Rating;
import org.controlsfx.glyphfont.FontAwesome;
import org.matt.App;
import org.matt.dataBeans.CastData;
import org.matt.dataBeans.MovieData;
import org.matt.sql.MovieMethods;
import org.matt.sql.UserMethods;

import java.net.URL;
import java.util.ResourceBundle;

import static org.matt.controllers.GlobalProperties.*;
import static org.matt.sql.UserMethods.addUserMovieRating;
import static org.matt.sql.UserMethods.updateMovieRating;

public class DetailsController implements Initializable {

    @FXML
    private Label movieTitle;
    @FXML
    private Label movieIdLabel;
    @FXML
    private Label movieRuntime;
    @FXML
    private Hyperlink movieUrl;
    @FXML
    private Label movieAvgVote;
    @FXML
    private Label movieVoteCount;
    @FXML
    private TextArea movieOverview;
    @FXML
    private Label ratingText;
    @FXML
    private Rating userRating;
    @FXML
    private Button updateMovieDB;
    @FXML
    private Button movieCastButton;
    @FXML
    private Button movieGenreButton;
    @FXML
    private Button closeWindowButton;
    @FXML
    private ScrollPane overviewScrollPane;
    @FXML
    private GridPane gridPane;

    private MovieData movie;
    private ObservableList<CastData> castList;
    private TableView<CastData> table = new TableView<>();
    private TableColumn<CastData, String> actorCol = new TableColumn<>("Actor");
    private TableColumn<CastData, String> characterCol = new TableColumn<>("Character");
    private TableColumn<CastData, String> genderCol = new TableColumn<>("Gender");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movie = MovieMethods.getMovieData();
        movieTitle.setText(movie.getTitle());
        movieIdLabel.setText(String.valueOf(movie.getId()));
        movieRuntime.setText(String.valueOf(movie.getRuntime()));
        movieUrl.setText(movie.getTitle() + " homepage");
        movieUrl.setOnAction(actionEvent -> App.getInstance().getHostServices().showDocument(movie.getHomePage()));
        updateMovieDB.setGraphic(gfxGlyph(FontAwesome.Glyph.REFRESH, Color.WHITE, 2));
        closeWindowButton.setGraphic(gfxGlyph(FontAwesome.Glyph.CLOSE, Color.WHITE, 2));
        updateVotes();
        movieOverview.setText(movie.getOverview());
        overviewScrollPane.setFitToWidth(true);
        overviewScrollPane.setContent(movieOverview);
        Bindings.bindBidirectional(ratingText.textProperty(), userRating.ratingProperty(), new NumberStringConverter());
    }

    private void updateVotes() {
        movie = MovieMethods.getMovieData();
        movieAvgVote.setText(String.valueOf(movie.getVoteAvg()));
        movieVoteCount.setText(String.valueOf(movie.getVoteCount()));
    }

    @FXML
    private void setRating(MouseEvent mouseEvent) {
        setCurrentMovieId(movie.getId());
        int scale = (int) Math.pow(10, 1);
        double rating = (double) Math.round(userRating.getRating() * scale) / scale;
        updateMovieDB.setVisible(true);
        userRating.setRating(rating);
        userRating.setRating(rating);

        updateMovieDB.setOnAction(actionEvent -> {
            boolean hasRating = UserMethods.hasMovieRating();
            if (hasRating) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Entry already exists");
                alert.setHeaderText(null);
                alert.setContentText("You already rated this movie");
                alert.showAndWait();
            } else {
                updateMovieRating(rating);
                addUserMovieRating(rating);
                updateVotes();
                updateMovieDB.setDisable(true);
            }
        });
    }

    @FXML
    private void closeDetailsWindow(ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void showCastMembers(ActionEvent actionEvent) {
        castList = MovieMethods.getCastViewData();
        table.getColumns().clear();
        actorCol.setCellValueFactory(data -> data.getValue().actorProperty());
        characterCol.setCellValueFactory(data -> data.getValue().characterProperty());
        genderCol.setCellValueFactory(data -> data.getValue().genderProperty());
        actorCol.setSortable(false);
        characterCol.setSortable(false);
        genderCol.setSortable(false);
        table.getColumns().addAll(actorCol,characterCol, genderCol);

        Pagination pagination = new Pagination((castList.size() / ROWS_PER_PAGE + 1), 0);
        pagination.setPageFactory(this::createPage);
        PopOver popOver = new PopOver(pagination);
        popOver.arrowLocationProperty().setValue(PopOver.ArrowLocation.RIGHT_CENTER);
        popOver.show(movieCastButton);
    }

    private Node createPage(int pageIndex) {

        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, castList.size());
        table.setItems(FXCollections.observableArrayList(castList.subList(fromIndex, toIndex)));
        autoResizeColumns(table);

        return new VBox(table);
    }

    @FXML
    private void showGenres(ActionEvent actionEvent) {
        ObservableList<String> genreList = MovieMethods.getGenreData();
        ListView<String> listView = new ListView<>(genreList);

        listView.prefHeightProperty().bind(Bindings.size(genreList).multiply(26));

        VBox vBox = new VBox(listView);
        //Create PopOver and add look and feel
        PopOver popOver = new PopOver(vBox);


        popOver.show(movieGenreButton);
    }
}
