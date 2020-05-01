package com.kashyapmedia.moviesdemo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;
import com.kashyapmedia.moviesdemo.R;
import com.kashyapmedia.moviesdemo.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private FragmentHomeBinding binding;
    private FragmentStateAdapter homePagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    
    @Override
    public void onDestroyView() {
        binding=null;
        super.onDestroyView();
    }


    private void setUp(){
        homePagerAdapter=new HomePagerAdapter(this);
        binding.viewPager.setAdapter(homePagerAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager,(tab, position) ->{
            switch (position){
                case 0:{
                    tab.setText(R.string.now_playing);
                    break;
                }
                case 1:{
                    tab.setText(R.string.upcoming);
                    break;
                }
                default:{
                    tab.setText("Unknown");
                    break;
                }
            }

        }
        ).attach();
    }
}
