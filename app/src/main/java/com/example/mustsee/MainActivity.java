package com.example.mustsee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import org.json.JSONObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import adapter.MovieAdapter;
import data.MainViewModel;
import data.Movie;
import utils.JSONUtils;
import utils.NetWorkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private RecyclerView recyclerViewPoster;
    private Switch switchSort;
    private TextView textViewPopularity;
    private TextView textViewTopRated;
    private ProgressBar progressBarLoading;

    private MovieAdapter adapter;
    private MainViewModel viewModel;
    private LoaderManager loaderManager;
    private static int page = 1;
    private static int methodOfSort;
    private static String lang;

    private static final int LOADER_ID = 4375;
    private static boolean isLoading = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavorite:
                Intent intentToFavorite = new Intent(this, FavoriteActivity.class);
                startActivity(intentToFavorite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getColumnCount() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels/displayMetrics.density);
        return width / 185 > 2 ? width / 185 : 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lang = Locale.getDefault().getLanguage();
        loaderManager = LoaderManager.getInstance(this);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewTopRated = findViewById(R.id.textViewTopRated);
        switchSort = findViewById(R.id.switchSort);
        recyclerViewPoster = findViewById(R.id.recyclerViewPoster);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
        adapter = new MovieAdapter();
        recyclerViewPoster.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
        recyclerViewPoster.setAdapter(adapter);

        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                page = 1;
                setMethodOfSort(isChecked);
            }
        });
        switchSort.setChecked(false);

        adapter.setOnPosterClickListner(new MovieAdapter.OnPosterClickListner() {
            @Override
            public void onPosterClick(int position) {
                Movie movie = adapter.getMovies().get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
            }
        });
            adapter.setOnReachEndListner(new MovieAdapter.OnReachEndListner() {
                @Override
                public void onReachEnd() {
                    if(! isLoading) {
                        downLoadData(methodOfSort, page);
                    }
                }
            });
        LiveData<List<Movie>> moviesFromLiveData = viewModel.getMovies();
        moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(page == 1) {
                    adapter.setMovies(movies);
                }
            }
        });
    }

    public void onClickSetPopularity(View view) {
        switchSort.setChecked(false);
    }

    public void onClickSetTopRated(View view) {
        switchSort.setChecked(true);
    }

    private void setMethodOfSort (boolean isTopRated) {
        if(isTopRated){
            methodOfSort = NetWorkUtils.TOP_RATED;
            textViewTopRated.setTextColor(getResources().getColor(R.color.design_default_color_secondary));
            textViewPopularity.setTextColor(getResources().getColor(R.color.white));
        } else {
            methodOfSort = NetWorkUtils.POPULARITY;
            textViewPopularity.setTextColor(getResources().getColor(R.color.design_default_color_secondary));
            textViewTopRated.setTextColor(getResources().getColor(R.color.white));
        }
        downLoadData(methodOfSort, page);
    }

    private void downLoadData(int methodOfSort, int page) {
        URL url = NetWorkUtils.buildURL(methodOfSort, page, lang);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(LOADER_ID, bundle, this);
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        NetWorkUtils.JSONLoader jsonLoader = new NetWorkUtils.JSONLoader(this, args);
        jsonLoader.setOnStartLoadingListner(new NetWorkUtils.JSONLoader.OnStartLoadingListner() {
            @Override
            public void onStartLoading() {
                progressBarLoading.setVisibility(View.VISIBLE);
                isLoading = true;
            }
        });
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(data);
        if(movies != null && !movies.isEmpty()) {
            if(page == 1){
                viewModel.deleteAllMovies();
                adapter.clear();
            }
            viewModel.deleteAllMovies();
            for(Movie movie : movies) {
                viewModel.insertMovie(movie);
            }
            adapter.addMovies(movies);
            page++;
        }
        isLoading = false;
        progressBarLoading.setVisibility(View.INVISIBLE);
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}