package com.example.hometutor.fragments.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.hometutor.MainActivity;
import com.example.hometutor.R;
import com.example.hometutor.classes.ChangeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignIn extends Fragment implements ChangeFragment.FragmentChange {
    private EditText edtEmail, edtPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = view.findViewById(R.id.signIn_email);
        edtPassword = view.findViewById(R.id.signIn_password);
        progressBar = view.findViewById(R.id.signIn_progress);

        Button signIn = view.findViewById(R.id.sign_button);
        signIn.setOnClickListener(v-> sendInputsForAuthentication());


        TextView newUser = view.findViewById(R.id.signIn_newUser);
        newUser.setOnClickListener(v -> changingFragment(new ChangeFragment()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            goToMainActivity();
        }
    }

    private void sendInputsForAuthentication() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Invalid email");
            wrongAnimationEffect(edtEmail);
            edtEmail.requestFocus();
            return;
        }

        if (password.length() < 6){
            edtPassword.setError("at least 6 characters");
            wrongAnimationEffect(edtPassword);
            edtPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        authentication(email, password);
    }

    private void authentication(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        goToMainActivity();
                    }else {
                        Toast.makeText(getContext(), "User doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                });
    }

    @Override
    public void changingFragment(ChangeFragment change) {
        change.OneFragmentToAnother(R.id.authentication_layout,this, new SignUp());
    }

    private void goToMainActivity(){
        startActivity(new Intent(getActivity(), MainActivity.class));
        Objects.requireNonNull(getActivity()).finish();
    }

    private void wrongAnimationEffect(View view){
        YoYo.with(Techniques.Shake)
                .duration(500)
                .playOn(view);
    }

    //TODO Facebook and google authentication
}