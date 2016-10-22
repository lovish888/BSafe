package com.insane.lovish.informe;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText mEmailText;
    EditText mPasswordText;
    Button mLoginButton;
    TextView mSignUpLink;
    TextView mForgotPassword;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, InformeHome.class);
                    //Toast.makeText(LoginActivity.this,"Logged In",Toast.LENGTH_SHORT).show();
                    //Deleting navigation history to prevent user from going back.
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                   //  User is signed out
                    //Toast.makeText(LoginActivity.this,"Used is logged out",Toast.LENGTH_SHORT).show();
                }
            }
        };
        findIds();
        onClickListeners();
    }

    private void onClickListeners() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        mSignUpLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                EnterEmailDialogFragment fragment = new EnterEmailDialogFragment();
                fragment.show(manager, "emailDialog");
            }
        });
    }

    public void findIds() {
        mEmailText = (EditText) findViewById(R.id.input_email);
        mPasswordText = (EditText) findViewById(R.id.input_password);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        mSignUpLink = (TextView) findViewById(R.id.link_signup);
        mForgotPassword = (TextView) findViewById(R.id.forget_password);
    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        onAppStart();
    }

    public void onAppStart() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);                       //For unknown loading time.
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        mLoginButton.setEnabled(false);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (isNetworkConnected()) {
                            new CheckInternet().execute();
                        } else {
                            progressDialog.dismiss();
                            onLoginFailed();
                            ErrorDialog();
                        }
                    }
                }, 3000);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null);
    }

    public void ErrorDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Sorry no Internet connectivity detected. Please reconnect and try again.");
        builder.setTitle("No Internet Connection");
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onAppStart();
            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onLoginSuccess() {

        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Filter", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Failed " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void onLoginFailed() {
        mLoginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 30) {
            mPasswordText.setError("between 4 and 30 alphanumeric characters");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public class CheckInternet extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {
                Log.e("Filter", "Error checking internet connection", e);
                return false;
            }
        }

        @Override
        public void onPostExecute(Boolean result) {

            progressDialog.setIndeterminate(false);
            progressDialog.dismiss();
            mLoginButton.setEnabled(true);
            if (result) {
                onLoginSuccess();
            } else {
                ErrorDialog();
            }
        }
    }
}
