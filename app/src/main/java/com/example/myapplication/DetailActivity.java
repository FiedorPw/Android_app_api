package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.models.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView detailPoster = findViewById(R.id.detailPoster);
        TextView detailTitle = findViewById(R.id.detailTitle);
        TextView detailReleaseDate = findViewById(R.id.detailReleaseDate);
        TextView detailOverview = findViewById(R.id.detailOverview);

        // wyciągnie obiektu movie z intentu
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_MOVIE)) {
            movie = (Movie) intent.getSerializableExtra(EXTRA_MOVIE);

            if (movie != null) {
                // aktualizacja widoku
                detailTitle.setText(movie.getTitle());
                detailReleaseDate.setText("Release date: " + movie.getReleaseDate());
                detailOverview.setText(movie.getOverview());

                // Poster
                Picasso.get()
                        .load(movie.getPosterUrl())
                        .placeholder(R.drawable.favicon)
                        .into(detailPoster);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // przekazanie tytułu filmu do MainActivity
        if (movie != null) {
            Intent data = new Intent();
            data.putExtra("clickedTitle", movie.getTitle());
            setResult(RESULT_OK, data);
        }
        super.onBackPressed();
    }
}
