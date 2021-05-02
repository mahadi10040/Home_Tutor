package com.example.hometutor.fragments.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hometutor.R;
import com.example.hometutor.classes.ChangeFragment;

public class SignIn extends Fragment implements ChangeFragment.FragmentChange {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        TextView newUser = view.findViewById(R.id.signIn_newUser);
        newUser.setOnClickListener(v -> changingFragment(new ChangeFragment()));

        return view;
    }

    @Override
    public void changingFragment(ChangeFragment change) {
        change.OneFragmentToAnother(R.id.authentication_layout,this, new SignUp());
    }
}