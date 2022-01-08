package org.matt.controllers;

import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.glyphfont.FontAwesome;
import org.matt.dataBeans.WatchlistData;
import org.matt.scenes.SceneChanger;
import org.matt.scenes.Views;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.matt.controllers.GlobalProperties.*;
import static org.matt.sql.UserMethods.*;

public class WatchlistController implements Initializable, TableInterface {

    @FXML
    private TableView<WatchlistData> tableView;
    @FXML
    private TableColumn<WatchlistData, String> title;
    @FXML
    private TableColumn<WatchlistData, Integer> runtime;
    @FXML
    private TableColumn<WatchlistData, LocalDate> releaseDate;
    @FXML
    private TableColumn<WatchlistData, LocalDateTime> timestamp;
    @FXML
    private Button closeButton;
    @FXML
    private Label userLabel;
    @FXML
    private Label removeLabel;
    @FXML
    private Label movieCountLabel;

    private ObservableList<WatchlistData> data;
    private final FadeTransition fadeIn = new FadeTransition(Duration.millis(3000));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateWindow();

        title.setSortable(false);
        runtime.setSortable(false);
        releaseDate.setSortable(false);
        timestamp.setSortable(false);

        fadeIn.setNode(removeLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(2);
        fadeIn.setAutoReverse(true);

        userLabel.setText(getUser().getName() + " Watchlist");
        closeButton.setGraphic(gfxGlyph(FontAwesome.Glyph.CLOSE, Color.WHITE, 2));
    }

    public void updateWindow() {
        data = getUserWatchlist();
        movieCountLabel.setText(countWatchlist() + " movies on your watchlist");
        handleRowSelect();
        createTable();
        addButtonToTable();
        Pagination pagination = new Pagination((int) Math.ceil(data.size() * 1.0 / ROWS_PER_PAGE), 0);
        createPage(pagination.getCurrentPageIndex());
        pagination.setPageFactory(this::createPage);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> createPage(newValue.intValue()));
    }

    @Override
    public void createTable() {
        tableView.getColumns().clear();
        title.setCellValueFactory(x -> x.getValue().titleProperty());
        runtime.setCellValueFactory(x -> x.getValue().runtimeProperty().asObject());
        releaseDate.setCellValueFactory(x -> x.getValue().releaseDateProperty());
        timestamp.setCellValueFactory(x -> x.getValue().timestampProperty());

        tableView.getColumns().addAll(title, runtime, releaseDate, timestamp);
    }

    @Override
    public Node createPage(int pageIndex) {
        int rowsPerPage = 12;
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
        tableView.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
        autoResizeColumns(tableView);

        return tableView;
    }

    @Override
    public void handleRowSelect() {
        tableView.setRowFactory(tv -> {
            TableRow<WatchlistData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    WatchlistData rowData = row.getItem();
                    setCurrentMovieId(rowData.getMovie_id());
                }
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    SceneChanger.openNewScene(Views.DETAILS);
                }
            });
            return row;
        });
    }

    private void addButtonToTable() {
        TableColumn<WatchlistData, Void> colBtn = new TableColumn("             ");

        Callback<TableColumn<WatchlistData, Void>, TableCell<WatchlistData, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<WatchlistData, Void> call(final TableColumn<WatchlistData, Void> param) {
                final TableCell<WatchlistData, Void> cell = new TableCell<>() {

                    private final JFXButton btn = new JFXButton(); {
                        btn.setOnAction((ActionEvent event) -> {
                            WatchlistData data = getTableView().getItems().get(getIndex());
                            setCurrentMovieId(data.getMovie_id());
                            removeLabel.setVisible(false);
                            removeLabel.setText(data.getTitle() + " removed from watchlist");

                            Stage stage = (Stage) btn.getScene().getWindow();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Watchlist removal");
                            alert.setHeaderText(null);
                            alert.setContentText("You are about to remove " + data.getTitle() + ", continue?");
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.initOwner(stage);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                deleteMovieFromWatchlist();
                                updateWindow();
                                if (!removeLabel.isVisible()) {
                                    removeLabel.setVisible(true);
                                    fadeIn.playFromStart();
                                }
                            } else if (result.get() == ButtonType.CANCEL) {
                                alert.hide();
                            }

                        });
                        btn.getStyleClass().add("button-raised");
                        btn.setGraphic(gfxGlyph(FontAwesome.Glyph.REMOVE, Color.RED, 2));
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
        tableView.getColumns().add(colBtn);
    }

    @FXML
    private void closeWatchlistWindow(ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }
}
