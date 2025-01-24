package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomMovieAdapter extends BaseAdapter {

    private final Context context;
    private final List<Movie> movieList;
    private final LayoutInflater inflater;

    public CustomMovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movieList = movies;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null){
            rowView = inflater.inflate(R.layout.activity_custom_list_view, parent, false);
        }
        ImageView imgView = rowView.findViewById(R.id.imageIcon);
        TextView txtView = rowView.findViewById(R.id.textView);

        Movie movie = movieList.get(position);

        txtView.setText(movie.getTitle());

        // w przypadku braku obrazka, ustawienie domyślnej ikony filmu
        Picasso.get()
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.favicon)
                .into(imgView);

        return rowView;
    }
}
