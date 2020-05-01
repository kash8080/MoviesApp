package com.kashyapmedia.moviesdemo.ui.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.kashyapmedia.moviesdemo.R;
import com.kashyapmedia.moviesdemo.databinding.ActivityMovieDetailBinding;

public class MovieDetailActivity extends AppCompatActivity {

    private ActivityMovieDetailBinding binding;

    public static void startThisActivity(Context context,int movieId){
        Intent intent=new Intent(context,MovieDetailActivity.class);
        intent.putExtra("DATA",movieId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        if(getIntent()==null){
            finish();
            return;
        }
        int movieId=getIntent().getIntExtra("DATA",-1);
        if(movieId<=0){
            finish();
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame,MovieDetailFragment.newInstance(movieId))
                .commit();

    }

    @Override
    protected void onDestroy() {
        binding=null;
        super.onDestroy();
    }
}
