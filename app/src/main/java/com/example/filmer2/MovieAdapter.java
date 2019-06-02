package com.example.filmer2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Utilities.NetworkUtils.Movie;

public class MovieAdapter extends BaseAdapter {

    public static final String MOVIE_BASE_URL="https://image.tmdb.org/t/p/w185";        //All the paths for the poster start this way, w185 is the size

    private Context mContext;
    ArrayList<Movie> list;

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        this.mContext = context;
        this.list = movieList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Movie getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        Movie movies = getItem(position);
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(200, 300));
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setAdjustViewBounds(true);        //Makes the image have the bounds of the placeholder drawable
            relativeLayout.addView(imageView);
        } else {
            imageView = (ImageView) convertView;
        }

        //load data into the ImageView using Picasso

        //To get a better idea of drawables and how to use them.
        //https://developer.android.com/guide/topics/resources/drawable-resource#Bitmap

        //Our placeholder image has to be in the file "drawable"
        Picasso.get().load(MOVIE_BASE_URL + movies.getPosterPath()).placeholder(R.drawable.image_placeholder).into(imageView);

        return imageView;
    }

}
