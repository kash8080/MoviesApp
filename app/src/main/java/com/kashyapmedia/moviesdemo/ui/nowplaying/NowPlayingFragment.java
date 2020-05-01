package com.kashyapmedia.moviesdemo.ui.nowplaying;

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

import com.kashyapmedia.moviesdemo.databinding.FragmentNowPlayingBinding;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;
import com.kashyapmedia.moviesdemo.repository.Resource;

import java.util.List;

public class NowPlayingFragment extends Fragment {
    private static final String TAG = "NowPlayingFragment";

    private NowPlayingViewModel model;
    private FragmentNowPlayingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(NowPlayingViewModel.class);

        model.getMovieData().observe(NowPlayingFragment.this, new Observer<Resource<List<MovieEntity>>>() {
            @Override
            public void onChanged(Resource<List<MovieEntity>> listResource) {
                switch (listResource.getStatus()){
                    case SUCCESS:{
                        Log.d(TAG, "onChanged: success "+listResource.getData().size());
                        break;
                    }
                    case ERROR:{
                        Log.d(TAG, "onChanged: error "+listResource.getMessage());

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
        super.onDestroyView();
        binding=null;
    }
}
