package com.android.abbas.DogCEO.fragments;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.android.abbas.DogCEO.MainActivity;
import com.android.abbas.DogCEO.interfaces.NavigationHost;

public class BaseFragment extends Fragment {

    private NavigationHost navigationHost;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            navigationHost = (NavigationHost) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigationHost = null;
    }

    public NavigationHost getNavigationHost() {
        return navigationHost;
    }
}
