package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mustsee.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import data.Movie;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private OnPosterClickListner onPosterClickListner;
    private OnReachEndListner onReachEndListner;

    public MovieAdapter() {
        movies = new ArrayList<>();
    }

    public interface OnPosterClickListner {
        void onPosterClick(int position);
    }

    public interface OnReachEndListner {
        void onReachEnd();
    }

    public void setOnPosterClickListner(OnPosterClickListner onPosterClickListner) {
        this.onPosterClickListner = onPosterClickListner;
    }

    public void setOnReachEndListner(OnReachEndListner onReachEndListner) {
        this.onReachEndListner = onReachEndListner;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        if(movies.size() >= 20 && position > movies.size() - 4 && onReachEndListner != null) {
            onReachEndListner.onReachEnd();
        }
        Movie movie = movies.get(position);
        Picasso.get().load(movie.getPosterPath()).into(holder.imageViewSmallPoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewSmallPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
            itemView.setOnClickListener(v -> {
                if(onPosterClickListner != null) {
                    onPosterClickListner.onPosterClick(getAdapterPosition());
                }
            });
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void clear() {
        this.movies.clear();
        notifyDataSetChanged();
    }
}
