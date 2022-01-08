package org.matt.controllers;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.glyphfont.FontAwesome;
import org.matt.dataBeans.MovieData;
import org.matt.scenes.SceneChanger;
import org.matt.scenes.Views;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.matt.controllers.GlobalProperties.*;
import static org.matt.sql.MovieMethods.createInitData;
import static org.matt.sql.UserMethods.addMovieToWatchlist;
import static org.matt.sql.UserMethods.getUser;


public class MainController implements Initializable {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label addToWatchListLabel;
    @FXML
    private Pagination pagination;
    @FXML
    private TextField searchText;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem details;
    @FXML
    private MenuItem add;
    @FXML
    private TableView<MovieData> movieTable;
    @FXML
    private TableColumn<MovieData, String> title;
    @FXML
    private TableColumn<MovieData, Integer> runtime;
    @FXML
    private TableColumn<MovieData, Double> avgVote;
    @FXML
    private TableColumn<MovieData, Integer> voteCount;
    @FXML
    private TableColumn<MovieData, LocalDate> releaseDate;
    @FXML
    private Button updateButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button logoutButton;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu userMenu;
    @FXML
    private MenuItem watchlistMenuButton;
    @FXML
    private MenuItem otherUsersButton;
    @FXML
    private MenuItem ratingsButton;
    @FXML
    private MenuItem aboutButton;

    private ObservableList<MovieData> data;
    private FilteredList<MovieData> filteredData;
    private FadeTransition fadeIn = new FadeTransition(Duration.millis(3000));


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = createInitData();
        filteredData = new FilteredList<>(data, b -> true);
        movieTable.setContextMenu(contextMenu);
        handleRowSelect();
        createTable();
        addButtonToTable();
        filterSearch();
        welcomeLabel.setText("Logged in as: " + getUser().getName());
        updateButton.setGraphic(gfxGlyph(FontAwesome.Glyph.REFRESH, Color.WHITE, 2));
        quitButton.setGraphic(gfxGlyph(FontAwesome.Glyph.POWER_OFF, Color.WHITE, 2));
        logoutButton.setGraphic(gfxGlyph(FontAwesome.Glyph.SIGN_OUT, Color.WHITE, 2));
        fadeIn.setNode(addToWatchListLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(2);
        fadeIn.setAutoReverse(true);
        userMenu.setText("User: " + getUser().getName());

    }

    @FXML
    private void updateMainWindow(ActionEvent actionEvent) {
        data = createInitData();
        filteredData = new FilteredList<>(data, b -> true);
        handleRowSelect();
        createTable();
        addButtonToTable();
        filterSearch();
    }

    private void filterSearch() {
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(movie -> newValue == null || newValue.isEmpty() || newValue.isBlank() || movie.getTitle().toLowerCase()
                    .contains(newValue.toLowerCase()) || String.valueOf(movie.getRuntime()).toLowerCase()
                    .contains(newValue.toLowerCase()) || String.valueOf(movie.getVoteAvg()).toLowerCase()
                    .contains(newValue.toLowerCase()) || String.valueOf(movie.getVoteCount()).toLowerCase()
                    .contains(newValue.toLowerCase()) || String.valueOf(movie.getReleaseDate()).toLowerCase()
                    .contains(newValue.toLowerCase()) || movie.getHomePage().toLowerCase()
                    .contains(newValue.toLowerCase()) || movie.getOverview().toLowerCase()
                    .contains(newValue.toLowerCase()));
            pagination.setPageCount((int) Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE));
            createPage(pagination.getCurrentPageIndex());
        });


        int totalPage = (int) (Math.ceil(data.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        createPage(0);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> createPage(newValue.intValue()));
    }

    private void addButtonToTable() {
        TableColumn<MovieData, Void> colBtn = new TableColumn("                                               ");

        Callback<TableColumn<MovieData, Void>, TableCell<MovieData, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<MovieData, Void> call(final TableColumn<MovieData, Void> param) {
                final TableCell<MovieData, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Add to Watchlist");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            MovieData data = getTableView().getItems().get(getIndex());
                            setCurrentMovieId(data.getId());
                            addToWatchListLabel.setVisible(false);
                            addToWatchListLabel.setText(data.getTitle() + " added to your Watchlist");
                            if (!addToWatchListLabel.isVisible()) {
                                addToWatchListLabel.setVisible(true);
                                fadeIn.playFromStart();
                            }
                            addMovieToWatchlist();
                        });
                        btn.setGraphic(gfxGlyph(FontAwesome.Glyph.PLUS_SQUARE, Color.WHITE, 2));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        movieTable.getColumns().add(colBtn);
    }

    public void createTable() {
        movieTable.getColumns().clear();
        title.setCellValueFactory(x -> x.getValue().titleProperty());
        runtime.setCellValueFactory(x -> x.getValue().runtimeProperty().asObject());
        avgVote.setCellValueFactory(x -> x.getValue().voteAvgProperty().asObject());
        voteCount.setCellValueFactory(x -> x.getValue().voteCountProperty().asObject());
        releaseDate.setCellValueFactory(x -> x.getValue().releaseDateProperty());


        movieTable.getColumns().addAll(title, runtime, avgVote, voteCount, releaseDate);
    }


    private void createPage(int index) {
        int fromIndex = index * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, data.size());

        int minIndex = Math.min(toIndex, filteredData.size());
        SortedList<MovieData> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(movieTable.comparatorProperty());

        movieTable.setItems(sortedData);
        autoResizeColumns(movieTable);
    }

    public void handleRowSelect() {

        movieTable.setRowFactory(tv -> {
            TableRow<MovieData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                MovieData rowData = row.getItem();
                setCurrentMovieId(rowData.getId());

                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    SceneChanger.openNewScene(Views.DETAILS);
                }
                details.setOnAction(actionEvent -> SceneChanger.openNewScene(Views.DETAILS));
            });
            return row;
        });
    }

    @FXML
    private void quitApp(ActionEvent actionEvent) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Application");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit?");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else if (result.get() == ButtonType.CANCEL) {
            alert.hide();
        }
    }

    @FXML
    private void logoutUser(ActionEvent actionEvent) {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout " + getUser().getName());
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            SceneChanger.changeScene(Views.LOGIN, logoutButton);
        } else if (result.get() == ButtonType.CANCEL) {
            alert.hide();
        }
    }

    @FXML
    private void openWatchlistScene(ActionEvent actionEvent) {
        SceneChanger.openNewScene(Views.WATCHLIST);
    }

    @FXML
    private void OpenUsersScene(ActionEvent actionEvent) {
    }

    @FXML
    private void aboutPopup(ActionEvent actionEvent) {
        SceneChanger.openNewScene(Views.ABOUT);
    }
}
