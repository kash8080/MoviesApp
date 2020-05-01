package com.kashyapmedia.moviesdemo.ui.fav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kashyapmedia.moviesdemo.databinding.FragmentFavouritesBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {
    private static final String TAG = "FavouritesFragment";

    private FragmentFavouritesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
