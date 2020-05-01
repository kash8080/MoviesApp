package com.kashyapmedia.moviesdemo.ui.moviedetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kashyapmedia.moviesdemo.api.ApiClient;
import com.kashyapmedia.moviesdemo.databinding.FragmentMovieDetailsBinding;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;
import com.kashyapmedia.moviesdemo.repository.Resource;
import com.kashyapmedia.moviesdemo.util.ScreenUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {
    private static final String TAG = "UpcomingFragment";

    private MovieDetailViewModel model;
    private FragmentMovieDetailsBinding binding;

    int movieId;
    MovieEntity movieEntity;
    RequestOptions largeImage,smallOptions;

    public static MovieDetailFragment newInstance(int movieId) {

        Bundle args = new Bundle();
        args.putInt("DATA", movieId);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            movieId=getArguments().getInt("DATA");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        largeImage=new RequestOptions()
                .override(ScreenUtils.convertDpToPixels(300,getActivity()));

        smallOptions=new RequestOptions()
                .override(ScreenUtils.convertDpToPixels(150,getActivity()));

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(MovieDetailViewModel.class);

        model.getMovieData(movieId).observe(this, new Observer<Resource<MovieEntity>>() {
            @Override
            public void onChanged(Resource<MovieEntity> movieEntityResource) {
                switch (movieEntityResource.getStatus()){
                    case SUCCESS:{
                        Log.d(TAG, "onChanged: success "+movieEntityResource.getData());
                        movieEntity=movieEntityResource.getData();
                        refreshData();
                        break;
                    }
                    case ERROR:{
                        Log.d(TAG, "onChanged: error "+movieEntityResource.getMessage());

                        break;
                    }
                    case LOADING:{
                        Log.d(TAG, "onChanged: LOADING");
                        break;
                    }
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        binding=null;
        super.onDestroyView();
    }

    public void onLikeClick(MovieEntity movieEntity) {
        if(movieEntity.isFavourite){
            model.removeFavourite(movieEntity.id);
        }else{
            model.addTofavourites(movieEntity.id);
        }
    }
    private void refreshData(){
        if(movieEntity==null){
            return;
        }
        if(movieEntity.backdrop_path!=null) {
            Glide.with(this)
                    .load(ApiClient.IMAGEBASEURL+ApiClient.IMAGESIZE500+movieEntity.backdrop_path)
                    .apply(largeImage)
                    .into(binding.parallaxImage);
        }
        if(movieEntity.poster_path!=null) {

            Glide.with(this)
                    .load(ApiClient.IMAGEBASEURL+ApiClient.IMAGESIZEMEDIUM+movieEntity.poster_path)
                    .apply(smallOptions)
                    .into(binding.image);
        }

        binding.title.setText(movieEntity.title);
        binding.releaseDate.setText(movieEntity.release_date);
        binding.rating.setText(movieEntity.vote_average+"/10");
        Log.d(TAG, "refreshData: "+movieEntity.overview);
        binding.overview.setText(movieEntity.overview);

    }
}
