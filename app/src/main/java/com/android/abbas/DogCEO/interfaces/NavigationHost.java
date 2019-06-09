package com.android.abbas.DogCEO.interfaces;

import com.android.abbas.DogCEO.fragments.BaseFragment;

public interface NavigationHost {

    void onSwitchFragment(BaseFragment baseFragment);

    void onSwitchFragment(BaseFragment baseFragment, boolean addToBackStack);

}
