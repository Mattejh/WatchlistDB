package org.matt.dataBeans;

import javafx.beans.property.*;

import java.sql.Date;
import java.time.LocalDate;

public class MovieData {

  private final IntegerProperty id = new SimpleIntegerProperty(this,"id");
  private final StringProperty title = new SimpleStringProperty(this,"title");
  private final StringProperty overview = new SimpleStringProperty(this,"overview");
  private final DoubleProperty voteAvg = new SimpleDoubleProperty(this, "voteAvg");
  private final IntegerProperty voteCount = new SimpleIntegerProperty(this, "voteCount");
  private final IntegerProperty runtime = new SimpleIntegerProperty(this, "runtime");
  private final ObjectProperty<LocalDate> releaseDate = new SimpleObjectProperty<>(this, "releaseDate");
  private final StringProperty homePage = new SimpleStringProperty(this, "homePage");

  public MovieData(int id, String title, String overview, double voteAvg, int voteCount, int runtime, Date releaseDate, String homePage) {
    setId(id);
    setTitle(title);
    setOverview(overview);
    setVoteAvg(voteAvg);
    setVoteCount(voteCount);
    setRuntime(runtime);
    setReleaseDate(releaseDate);
    setHomePage(homePage);
  }

  public int getId() {
    return id.get();
  }

  public IntegerProperty idProperty() {
    return id;
  }

  public void setId(int id) {
    this.id.set(id);
  }

  public String getTitle() {
    return title.get();
  }

  public StringProperty titleProperty() {
    return title;
  }

  public void setTitle(String title) {
    this.title.set(title);
  }

  public String getOverview() {
    return overview.get();
  }

  public StringProperty overviewProperty() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview.set(overview);
  }

  public double getVoteAvg() {
    return voteAvg.get();
  }

  public DoubleProperty voteAvgProperty() {
    return voteAvg;
  }

  public void setVoteAvg(double voteAvg) {
    this.voteAvg.set(voteAvg);
  }

  public long getVoteCount() {
    return voteCount.get();
  }

  public IntegerProperty voteCountProperty() {
    return voteCount;
  }

  public void setVoteCount(int voteCount) {
    this.voteCount.set(voteCount);
  }

  public long getRuntime() {
    return runtime.get();
  }

  public IntegerProperty runtimeProperty() {
    return runtime;
  }

  public void setRuntime(int runtime) {
    this.runtime.set(runtime);
  }

  public LocalDate getReleaseDate() {
    return releaseDate.get();
  }

  public ObjectProperty<LocalDate> releaseDateProperty() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate.set(releaseDate.toLocalDate());
  }

  public String getHomePage() {
    return homePage.get();
  }

  public StringProperty homePageProperty() {
    return homePage;
  }

  public void setHomePage(String homePage) {
    this.homePage.set(homePage);
  }
}
