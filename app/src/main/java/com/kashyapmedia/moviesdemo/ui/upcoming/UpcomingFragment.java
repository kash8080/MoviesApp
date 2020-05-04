package com.kashyapmedia.moviesdemo.ui.upcoming;

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
import androidx.recyclerview.widget.GridLayoutManager;

import com.kashyapmedia.moviesdemo.R;
import com.kashyapmedia.moviesdemo.databinding.FragmentUpcomingBinding;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;
import com.kashyapmedia.moviesdemo.repository.Resource;
import com.kashyapmedia.moviesdemo.ui.common.SimpleMovieListAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements SimpleMovieListAdapter.MovieCallback {
    private static final String TAG = "UpcomingFragment";

    private UpcomingViewModel model;
    private FragmentUpcomingBinding binding;
    private SimpleMovieListAdapter movieListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUpcomingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        movieListAdapter=new SimpleMovieListAdapter(this,getActivity());
        binding.recView.setAdapter(movieListAdapter);
        binding.recView.setLayoutManager(new GridLayoutManager(getActivity(),getResources().getInteger(R.integer.movie_cols)));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(UpcomingViewModel.class);

        binding.imageError.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.INVISIBLE);
        String lang=getString(R.string.lang);
        model.getMovieData(lang).observe(this, new Observer<Resource<List<MovieEntity>>>() {
            @Override
            public void onChanged(Resource<List<MovieEntity>> listResource) {
                switch (listResource.getStatus()){
                    case SUCCESS:{
                        Log.d(TAG, "onChanged: success "+listResource.getData().size());
                        binding.imageError.setVisibility(View.INVISIBLE);
                        movieListAdapter.setList(listResource.getData());
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        break;
                    }
                    case ERROR:{
                        Log.d(TAG, "onChanged: error "+listResource.getMessage());
                        binding.imageError.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.INVISIBLE);

                        break;
                    }
                    case LOADING:{
                        binding.imageError.setVisibility(View.INVISIBLE);
                        binding.progressBar.setVisibility(View.VISIBLE);
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
        movieListAdapter = null;
        super.onDestroyView();
    }

    @Override
    public void onClick(MovieEntity movieEntity, int pos) {

    }

    @Override
    public void onLikeClick(MovieEntity movieEntity, int pos) {
        if(movieEntity.isFavourite){
            model.removeFavourite(movieEntity.id);
        }else{
            model.addTofavourites(movieEntity.id);
        }
    }
}
