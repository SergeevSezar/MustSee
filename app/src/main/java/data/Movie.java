package data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "movies")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private int id;
    private String title;
    private String originalTitle;
    private String overView;
    private String posterPath;
    private String backdropPath;
    private String releaseDate;
    private int voteCount;
    private double voteAverage;
    private String bigPosterPath;

    public Movie(int uniqueId, int id, String title, String originalTitle, String overView, String posterPath, String backdropPath, String releaseDate, int voteCount, double voteAverage, String bigPosterPath) {
        this.uniqueId = uniqueId;
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overView = overView;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.bigPosterPath = bigPosterPath;
    }

    @Ignore
    public Movie(int id, String title, String originalTitle, String overView, String posterPath, String backdropPath, String releaseDate, int voteCount, double voteAverage, String bigPosterPath) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overView = overView;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.bigPosterPath = bigPosterPath;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getBigPosterPath() {
        return bigPosterPath;
    }

    public void setBigPosterPath(String bigPosterPath) {
        this.bigPosterPath = bigPosterPath;
    }
}
