package com.kashyapmedia.moviesdemo.ui.fav;

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
import com.kashyapmedia.moviesdemo.databinding.FragmentFavouritesBinding;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;
import com.kashyapmedia.moviesdemo.repository.Resource;
import com.kashyapmedia.moviesdemo.ui.common.SimpleMovieListAdapter;
import com.kashyapmedia.moviesdemo.ui.moviedetails.MovieDetailActivity;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment implements  SimpleMovieListAdapter.MovieCallback {
    private static final String TAG = "FavouritesFragment";

    private FavouritesViewModel model;
    private FragmentFavouritesBinding binding;
    private SimpleMovieListAdapter movieListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        movieListAdapter=new SimpleMovieListAdapter(this,getActivity());
        binding.recView.setAdapter(movieListAdapter);
        binding.recView.setLayoutManager(new GridLayoutManager(getActivity(),getResources().getInteger(R.integer.movie_cols)));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(FavouritesViewModel.class);

        model.getFavourites().observe(this, new Observer<Resource<List<MovieEntity>>>() {
            @Override
            public void onChanged(Resource<List<MovieEntity>> listResource) {
                switch (listResource.getStatus()){
                    case SUCCESS:{
                        Log.d(TAG, "onChanged: success "+listResource.getData().size());
                        movieListAdapter.setList(listResource.getData());
                        if(listResource.getData().size()==0){
                            binding.noDataFound.setVisibility(View.VISIBLE);
                        }else{
                            binding.noDataFound.setVisibility(View.INVISIBLE);
                        }
                        break;
                    }
                    case ERROR:{
                        Log.d(TAG, "onChanged: error "+listResource.getMessage());
                        binding.noDataFound.setVisibility(View.VISIBLE);
                        break;
                    }
                    case LOADING:{
                        Log.d(TAG, "onChanged: LOADING");
                        binding.noDataFound.setVisibility(View.INVISIBLE);
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

    @Override
    public void onClick(MovieEntity movieEntity, int pos) {
        MovieDetailActivity.startThisActivity(getActivity(),movieEntity.id);
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
