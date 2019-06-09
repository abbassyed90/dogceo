package com.android.abbas.DogCEO;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.abbas.DogCEO.fragments.BaseFragment;
import com.android.abbas.DogCEO.fragments.DogBreedListFragment;
import com.android.abbas.DogCEO.interfaces.NavigationHost;

public class MainActivity extends AppCompatActivity implements NavigationHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onSwitchFragment(DogBreedListFragment.getInstance(),false);
    }

    @Override
    public void onSwitchFragment(BaseFragment baseFragment) {
        onSwitchFragment(baseFragment, true);
    }

    @Override
    public void onSwitchFragment(BaseFragment baseFragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, baseFragment);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(baseFragment.toString());
        }

        fragmentTransaction.commit();
    }
}
