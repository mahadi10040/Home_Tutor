package com.example.hometutor.fragments.splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hometutor.R;
import com.example.hometutor.classes.ChangeFragment;

public class Feature2 extends Fragment implements ChangeFragment.FragmentChange {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_feature2, container, false);

        ImageView next = view.findViewById(R.id.feature2_next);
        next.setOnClickListener(v->changingFragment(new ChangeFragment()));

        return view;
    }

    @Override
    public void changingFragment(ChangeFragment change) {
        change.OneFragmentToAnother(R.id.splash_layout, this, new Feature3());
    }
}