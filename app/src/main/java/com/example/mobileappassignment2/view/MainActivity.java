package com.example.mobileappassignment2.view;

import android.os.Bundle;
import android.os.StrictMode;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappassignment2.MovieAdapter;
import com.example.mobileappassignment2.R;
import com.example.mobileappassignment2.databinding.ActivityMainBinding;
import com.example.mobileappassignment2.model.MovieModel;
import com.example.mobileappassignment2.viewmodel.MovieViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter movieAdapter;
    private ArrayList<MovieModel> movieList = new ArrayList<>();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));

        movieAdapter = new MovieAdapter(this, movieList);
        binding.recyclerViewMovies.setAdapter(movieAdapter);

        // grant nerwork permisson
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        // Binding MovieViewModel
        MovieViewModel viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // Listening to LiveData changes
        viewModel.getMovies().observe(this, movies -> {
            movieAdapter.updateData(movies);
        });

        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.searchMovies(binding.editTextSearch.getText().toString(),
                        getString(R.string.API_KEY));
            }
        });
    }
}