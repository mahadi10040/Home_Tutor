package com.example.hometutor.fragments.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.hometutor.MainActivity;
import com.example.hometutor.R;
import com.example.hometutor.classes.ChangeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class SignIn extends Fragment implements ChangeFragment.FragmentChange {
    private EditText edtEmail, edtPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        mAuth = FirebaseAuth.getInstance();
        createRequest();

        edtEmail = view.findViewById(R.id.signIn_email);
        edtPassword = view.findViewById(R.id.signIn_password);
        progressBar = view.findViewById(R.id.signIn_progress);

        Button signIn = view.findViewById(R.id.sign_button);
        signIn.setOnClickListener(v-> sendInputsForAuthentication());

        ImageView google = view.findViewById(R.id.signIn_google);
        google.setOnClickListener(v->signInWithGoogle());

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

    //Password sign in starts here
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

    //Google sign in starts here
    private void createRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()), gso);
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        goToMainActivity();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //TODO facebook sign in left

    private void goToMainActivity(){
        startActivity(new Intent(getActivity(), MainActivity.class));
        Objects.requireNonNull(getActivity()).finish();
    }

    private void wrongAnimationEffect(View view){
        YoYo.with(Techniques.Shake)
                .duration(500)
                .playOn(view);
    }

    @Override
    public void changingFragment(ChangeFragment change) {
        change.OneFragmentToAnother(R.id.authentication_layout,this, new SignUp());
    }
}