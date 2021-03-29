package data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static MovieDataBase dataBase;
    private LiveData<List<Movie>> movies;
    private LiveData<List<FavoriteMovie>> favoriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        dataBase = MovieDataBase.getInstance(getApplication());
        movies = dataBase.movieDao().getAllMovies();
        favoriteMovies = dataBase.movieDao().getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public Movie getMovieById(int id) {
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllMovies() {
        new DeleteAllMovieTask().execute();
    }

    public void insertMovie(Movie movie) {
        new InsertMovieTask().execute(movie);
    }

    public void deleteMovie(Movie movie) {
        new DeleteMovieTask().execute(movie);
    }

    public void insertFavoriteMovie(FavoriteMovie favoriteMovie) {
        new InsertFavoriteTask().execute(favoriteMovie);
    }

    public void deleteFavoriteMovie(FavoriteMovie favoriteMovie) {
        new DeleteFavoriteTask().execute(favoriteMovie);
    }

    public FavoriteMovie getFavoriteMovieById(int id) {
        try {
            return new GetFavoriteMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetMovieTask extends AsyncTask<Integer, Void, Movie> {

        @Override
        protected Movie doInBackground(Integer... integers) {
            if(integers != null && integers.length > 0) {
                return dataBase.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class DeleteAllMovieTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            dataBase.movieDao().deleteAllMovies();
            return null;
        }
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies != null && movies.length > 0) {
                dataBase.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies != null && movies.length > 0){
                dataBase.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertFavoriteTask extends AsyncTask<FavoriteMovie, Void, Void> {

        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if(movies != null && movies.length > 0) {
                dataBase.movieDao().insertFavoriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteFavoriteTask extends AsyncTask<FavoriteMovie, Void, Void> {

        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if(movies != null && movies.length > 0){
                dataBase.movieDao().deleteFavoriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class GetFavoriteMovieTask extends AsyncTask<Integer, Void, FavoriteMovie> {

        @Override
        protected FavoriteMovie doInBackground(Integer... integers) {
            if(integers != null && integers.length > 0) {
                return dataBase.movieDao().getFavoriteMovieById(integers[0]);
            }
            return null;
        }
    }
}
