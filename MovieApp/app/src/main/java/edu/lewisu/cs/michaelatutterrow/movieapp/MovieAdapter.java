package edu.lewisu.cs.michaelatutterrow.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Moviee> movies;
    private Context context;
    private final MovieAdapterOnClickHandler clickHandler;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler){
        this.clickHandler = clickHandler;
    }

    public interface MovieAdapterOnClickHandler{
        void onClick(Moviee movie);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Moviee movie = movies.get(i);
        String title = movie.getTitle();
        int rank = movie.getRank();
        String titleRank = context.getResources().getString(R.string.rank_title, rank, title);
        movieViewHolder.movieDataTextView.setText(titleRank);

    }

    @Override
    public int getItemCount() {
        if(movies != null){
            return movies.size();
        }
        return 0;
    }

    public void setMovieData(List<Moviee> movieData){
        movies = movieData;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView movieDataTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieDataTextView = itemView.findViewById(R.id.movieDataTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Moviee movie = movies.get(adapterPosition);
            clickHandler.onClick(movie);

        }
    }
}
