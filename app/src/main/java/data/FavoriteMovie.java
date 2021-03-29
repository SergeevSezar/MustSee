package data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favorite_movies")
public class FavoriteMovie extends Movie {

    public FavoriteMovie(int uniqueId, int id, String title, String originalTitle, String overView, String posterPath, String backdropPath, String releaseDate, int voteCount, double voteAverage, String bigPosterPath) {
        super(uniqueId, id, title, originalTitle, overView, posterPath, backdropPath, releaseDate, voteCount, voteAverage, bigPosterPath);
    }

    @Ignore
    public FavoriteMovie(Movie movie) {
        super(movie.getUniqueId(), movie.getId(), movie.getTitle(), movie.getOriginalTitle(), movie.getOverView(), movie.getPosterPath(), movie.getBackdropPath(), movie.getReleaseDate(), movie.getVoteCount(), movie.getVoteAverage(), movie.getBigPosterPath());
    }
}
