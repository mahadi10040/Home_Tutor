package com.example.hometutor.classes;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class ChangeFragment {

    public void OneFragmentToAnother(int layoutId, Fragment currentFragment, Fragment changeToFragment){
        FragmentManager manager = Objects.requireNonNull(currentFragment.getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(layoutId, changeToFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public interface FragmentChange{
        void changingFragment(ChangeFragment change);
    }
}
