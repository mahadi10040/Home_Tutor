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

import java.util.Objects;

public class Welcome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button getStarted = view.findViewById(R.id.welcome_getStarted);
        getStarted.setOnClickListener(v -> goToFeature1());

        return view;
    }

    private void goToFeature1() {
        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.splash_layout, new Feature1());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}