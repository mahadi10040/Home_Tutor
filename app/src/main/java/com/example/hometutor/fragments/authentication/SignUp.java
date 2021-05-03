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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.hometutor.MainActivity;
import com.example.hometutor.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUp extends Fragment {
    private EditText edtFullName, edtEmail, edtPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAuth = FirebaseAuth.getInstance();

        edtEmail = view.findViewById(R.id.signUp_email);
        edtPassword = view.findViewById(R.id.signUp_password);
        edtFullName = view.findViewById(R.id.signup_fullName);
        progressBar = view.findViewById(R.id.signup_progressbar);

        Button signUp = view.findViewById(R.id.signUp_button);
        signUp.setOnClickListener(v->sendInputsForNewAccount());

        return view;
    }

    private void sendInputsForNewAccount() {
        String name = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (name.isEmpty()){
            edtFullName.setError("Field empty");
            wrongAnimationEffect(edtFullName);
            edtFullName.requestFocus();
            return;
        }

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
        createNewAccount(email, password);
        //TODO Need to do something with the name.
    }

    private void createNewAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Account created", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Account already exist", Toast.LENGTH_SHORT).show();
                        goToMainActivity();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                });
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
}