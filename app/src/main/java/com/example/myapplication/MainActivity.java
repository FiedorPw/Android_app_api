package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.models.Movie;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// importy do Javy
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // kod zapytania
    private static final int REQUEST_DETAILS = 123;
    private ListView listView;
    private CustomMovieAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();

    // liczba filmów do pobrania
    int NUM_MOVIES = 15;

    // Klucz do API TMDB
    private static final String TMDB_BEARER_TOKEN = "KEY";

    // wykorzystanie endpointu z popularnymi filmami
    private static final String TMDB_POPULAR_URL =
            "https://api.themoviedb.org/3/movie/popular?language=en-US&page=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // zawiera layout z ListView
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.customListView);
        adapter = new CustomMovieAdapter(this, movieList);
        listView.setAdapter(adapter);

        // Click handler
        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View view,
                                         int position, long id) -> {
            Movie clicked = movieList.get(position);
            // przejdz do DetailActivity
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_MOVIE, clicked);
                startActivityForResult(intent, REQUEST_DETAILS);
        });

        // pobierz filmy z TMDB
        fetchPopularMovies();
    }

    private void fetchPopularMovies() {
        // wykorzystanie nowego wątku do pobrania danych
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            // zbudownaie zapytania do api
            Request request = new Request.Builder()
                    .url(TMDB_POPULAR_URL)
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer " + TMDB_BEARER_TOKEN)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.body() == null) {
                    Log.e("MainActivity", "No response body");
                    return;
                }
                String jsonString = response.body().string();

                // parsowanie jsona
                Gson gson = new Gson();
                JsonObject obj = gson.fromJson(jsonString, JsonObject.class);
                JsonArray results = obj.getAsJsonArray("results");

                ArrayList<Movie> tempList = new ArrayList<>();

                // parsowanie elementów do zmiennych dla NUM_MOVIES filmów
                for (int i = 0; i < results.size() && i < NUM_MOVIES; i++) {
                    JsonObject item = results.get(i).getAsJsonObject();

                    String title = item.get("title").getAsString();
                    String posterPath = item.get("poster_path").getAsString();
                    String releaseDate = item.get("release_date").getAsString();
                    String overview = item.get("overview").getAsString();

                    // TMDB poster base URL
                    // e.g. "https://image.tmdb.org/t/p/w500"
                    String fullPosterUrl = "https://image.tmdb.org/t/p/w500" + posterPath;

                    Movie m = new Movie(title, fullPosterUrl, releaseDate, overview);
                    tempList.add(m);
                }

                // aktualizowanie widoku przez adapter
                runOnUiThread(() -> {
                    movieList.clear();
                    movieList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_DETAILS && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("clickedTitle")) {
                String title = data.getStringExtra("clickedTitle");
                // pokaż toast z tytułem filmu
                Toast.makeText(MainActivity.this, "Wyszedłeś z " + title, Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
