package com.example.filmer2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Utilities.NetworkUtils;
import Utilities.NetworkUtils.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Variables
    String popularMovies;
    String topRatedMovies;
    ArrayList<Movie> myPopularList;
    ArrayList<Movie> myTopTopRatedList;

    @BindView(R.id.movieCatalogue)
    GridView movieCatalogueGrid;

    @BindView(R.id.indeterminateBar)
    ProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myProgressBar.setVisibility(View.INVISIBLE); //Hide Progressbar by Default


        new FetchMovies().execute(); //Run the background tasks with async
    }


    //AsyncTask
    public class FetchMovies extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {         //Shows the progress bar
            super.onPreExecute();
            myProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Didn't add protection or encryption of any kind for the api key sorry professor hehe
            String popularMoviesURL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + "dc65648ae033b0600f07446a6b592bee";
            String topRatedMoviesURL = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=" + "dc65648ae033b0600f07446a6b592bee";


            myPopularList = new ArrayList<>();
            myTopTopRatedList = new ArrayList<>();

            try {
                if (NetworkUtils.networkStatus(MainActivity.this)) {
                    myPopularList = NetworkUtils.fetchData(popularMoviesURL); //Get popular movies
                    myTopTopRatedList = NetworkUtils.fetchData(topRatedMoviesURL); //Get top rated movies
                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void s) {         //Makes the progress bar hide again
            super.onPostExecute(s);
            myProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //Basically to put the menu button on the bar above the movies
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }


    @Override       //Populates the lists with data and passes them on to refresh list
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pop_movies) {
            refreshList(myPopularList);
        }
        if (id == R.id.top_movies) {
            refreshList(myTopTopRatedList);
        }
        return super.onOptionsItemSelected(item);
    }


    //Creates a new adapter with the new data and invalidate the current gridview to put the new one
    private void refreshList(ArrayList<Movie> list) {
        MovieAdapter adapter = new MovieAdapter(MainActivity.this, list);
        movieCatalogueGrid.invalidateViews();
        movieCatalogueGrid.setAdapter(adapter);
    }



}
