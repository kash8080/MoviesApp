package com.kashyapmedia.moviesdemo.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kashyapmedia.moviesdemo.ui.nowplaying.NowPlayingFragment;
import com.kashyapmedia.moviesdemo.ui.upcoming.UpcomingFragment;

public class HomePagerAdapter extends FragmentStateAdapter {
    public HomePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:{
                return new NowPlayingFragment();
            }
            case 1:{
                return new UpcomingFragment();
            }
            default:{
                return null;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
