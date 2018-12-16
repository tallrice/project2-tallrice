package edu.lewisu.cs.michaelatutterrow.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity{

    private final static String TAG = MovieDetailActivity.class.getSimpleName();

    Moviee movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");

        TextView titleTextView = findViewById(R.id.titleTextView);

        if(movie != null){
            titleTextView.setText(movie.getTitle());

            TextView descriptionTextView = findViewById(R.id.descriptionTextView);
            descriptionTextView.setText(movie.getDescription());

            ImageView view = findViewById(R.id.imageViewPoster);

            Picasso.Builder picassoBuilder = new Picasso.Builder(getApplicationContext());
            Picasso picasso = picassoBuilder.build();
            picasso.load(movie.getPoster())
                    .into(view);

        }else{
            titleTextView.setText(getResources().getString(R.string.download_error));
        }
    }
}
