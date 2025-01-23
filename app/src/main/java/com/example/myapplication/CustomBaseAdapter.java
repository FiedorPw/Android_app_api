package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String[] names;
    int[] images;
    LayoutInflater inflter;

    public CustomBaseAdapter(Context ctx, String[] names, int[] images){
        this.context = ctx;
        this.names = names;
        this.images = images;
        inflter = (LayoutInflater.from(ctx));
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.activity_custom_list_view, null);
        TextView txtView = (TextView) convertView.findViewById(R.id.textView);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.imageIcon);
        txtView.setText(names[position]);
        imgView.setImageResource(images[position]);
        return convertView;
    }
}
