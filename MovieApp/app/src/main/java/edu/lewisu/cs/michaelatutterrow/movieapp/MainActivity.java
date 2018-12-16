package edu.lewisu.cs.michaelatutterrow.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final String API_KEY = "787a634e0403914629c5871a8d44f989";
    private Spinner genreSpinner;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;

    @Override
    public void onClick(Moviee movie) {
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
        detailIntent.putExtra("movie", movie);
        startActivity(detailIntent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genreSpinner = findViewById(R.id.genre_spinner);
        progressBar = findViewById(R.id.progress_bar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);

    }

    public void getMoviesClick(View v) {
        String genre = genreSpinner.getSelectedItem().toString();
        getMovies(genre);
    }

    private Integer getGenreInt(String genre){

        Integer genreInt = 28;
        switch (genre) {
            case "Action":
                genreInt = 28;
                break;
            case "Adventure":
                genreInt = 12;
                break;
            case "Animation":
                genreInt = 16;
                break;
            case "Comedy":
                genreInt = 35;
                break;
            case "Crime":
                genreInt = 80;
                break;
            case "Documentary":
                genreInt = 99;
                break;
            case "Drama":
                genreInt = 18;
                break;
            case "Family":
                genreInt = 10751;
                break;
            case "Fantasy":
                genreInt = 14;
                break;
            case "History":
                genreInt = 36;
                break;
            case "Horror":
                genreInt = 27;
                break;
            case "Music":
                genreInt = 10402;
                break;
            case "Mystery":
                genreInt = 9648;
                break;
            case "Romance":
                genreInt = 10749;
                break;
            case "Science Fiction":
                genreInt = 878;
                break;
            case "Thriller":
                genreInt = 53;
                break;
            case "TV Movie":
                genreInt = 10770;
                break;
            case "War":
                genreInt = 10752;
                break;
            case "Western":
                genreInt = 37;
                break;
        }
        return genreInt;
    }
    private void getMovies(String genre) {

        Integer genreInt = getGenreInt(genre);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("language", "en")
                .appendQueryParameter("primary_release_year", "2018")
                .appendQueryParameter("with_release_type", "3")
                .appendQueryParameter("with_genres", genreInt.toString())
                .appendQueryParameter("sort_by", "popularity.desc")
                .appendQueryParameter("include_adult", "false")
                .appendQueryParameter("include_video", "false")
                .appendQueryParameter("page", "1");

        Uri moviesUri = builder.build();
        Log.d("uri", moviesUri.toString());

        DownloadMovie downloadMovies = new DownloadMovie(this);
        downloadMovies.execute(moviesUri);
    }

    public void goToIMBD(View view) {
    }

    private static class DownloadMovie extends
            AsyncTask<Uri, Void, ArrayList<Moviee>> {

        private WeakReference<MainActivity> activityReference;

        DownloadMovie(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = activityReference.get();
            activity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Moviee> doInBackground(Uri... uris) {
            OkHttpClient client = new OkHttpClient();
            String jsonData = null;
            ArrayList<Moviee> movies = new ArrayList<>();

            try{
                URL url = new URL(uris[0].toString());
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                Request request = builder.build();
                Response response = client.newCall(request).execute();

                if(response.body() != null){
                    jsonData = response.body().string();
                }else {
                    return null;
                }
            }catch(Exception e){
                Log.d(TAG, e.toString());
            }
            String title;
            String author;
            String description;
            String posterUrl;
            int rank;
            int numMovies = 10;

            try{
                JSONObject results = new JSONObject(jsonData);
                JSONArray movieList = results.getJSONArray("results");
                if(movieList.length() < 10)
                    numMovies = movieList.length();

                for(int i=0; i<numMovies; i++) {
                    JSONObject movieObject = movieList.getJSONObject(i);
                    posterUrl ="https://image.tmdb.org/t/p/w185_and_h278_bestv2/"
                        + movieObject.getString("poster_path");
                    rank = i+1;
                    title = movieObject.getString("title");
                    description = movieObject.getString("overview");

                    Moviee movie = new Moviee(rank, title, posterUrl, description);
                    movies.add(movie);
                }
                // Thread.sleep(2000);
                return movies;
            }catch(Exception e){
                Log.d(TAG, e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Moviee> movies) {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(View.INVISIBLE);
            if (movies != null) {
                activity.movieAdapter.setMovieData(movies);
            }

        }
    }
}
