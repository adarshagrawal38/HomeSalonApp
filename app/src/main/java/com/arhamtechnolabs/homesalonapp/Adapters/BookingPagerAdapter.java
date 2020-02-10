package com.arhamtechnolabs.homesalonapp.Adapters;

import com.arhamtechnolabs.homesalonapp.CompletedFragment;
import com.arhamtechnolabs.homesalonapp.OnGoingFragment;
import com.arhamtechnolabs.homesalonapp.ui.InviteCodeFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class BookingPagerAdapter extends FragmentStatePagerAdapter {


    public BookingPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new OnGoingFragment();
            case 1: return new CompletedFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "On going";
            case 1: return "Completed";

            default: return null;
        }
    }
}
