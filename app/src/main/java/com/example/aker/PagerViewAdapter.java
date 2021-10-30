package com.example.aker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.aker.ui.gallery.GalleryFragment;
import com.example.aker.ui.home.HomeFragment;
//import com.example.aker.ui.other.OtherFragment;
import com.example.aker.ui.profile.profileFragment;
import com.example.aker.ui.slideshow.SlideshowFragment;

class PagerViewAdapter extends FragmentPagerAdapter {
    public PagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new Allbet();
                break;
            case 1:
                fragment = new Allbet();
                break;
            case 2:
                fragment = new Allbet();
                break;
            case 3:
                fragment = new Allbet();
                break;
            case 4:
                fragment = new Allbet();
                break;
            case 5:
                fragment = new Allbet();
                break;
            case 6:
                fragment = new Allbet();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
