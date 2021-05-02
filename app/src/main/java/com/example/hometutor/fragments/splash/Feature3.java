package com.example.hometutor.fragments.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hometutor.AuthenticationActivity;
import com.example.hometutor.R;

import java.util.Objects;

public class Feature3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feature3, container, false);

        Button begin = view.findViewById(R.id.feature_begin);
        begin.setOnClickListener(v->goToAuthenticationActivity());

        return view;
    }

    private void goToAuthenticationActivity() {
        startActivity(new Intent(getActivity(), AuthenticationActivity.class));
        Objects.requireNonNull(getActivity()).finish();
    }
}