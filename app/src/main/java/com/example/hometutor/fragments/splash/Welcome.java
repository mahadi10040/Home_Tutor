package com.example.hometutor.fragments.splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hometutor.R;
import com.example.hometutor.classes.ChangeFragment;

import java.util.Objects;

public class Welcome extends Fragment implements ChangeFragment.FragmentChange {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button getStarted = view.findViewById(R.id.welcome_getStarted);
        getStarted.setOnClickListener(v -> changingFragment(new ChangeFragment()));

        return view;
    }

    @Override
    public void changingFragment(ChangeFragment change) {
        change.OneFragmentToAnother(R.id.splash_layout, this, new Feature1());
    }
}