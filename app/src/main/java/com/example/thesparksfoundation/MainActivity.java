package com.example.thesparksfoundation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;


public class MainActivity extends AppCompatActivity {

    TwitterLoginButton loginButton;
    TextView textView;
    int co;
    private FirebaseAuth mAuth;
    LoginButton loginButtonfa;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

//        loginButtonfa.setReadPermissions(Arrays.asList("email", "public_profile"));
       callbackManager = CallbackManager.Factory.create();


        //facebook
        loginButtonfa = findViewById(R.id.login_button1);


        //Twitter
//        textView=findViewById(R.id.te);
        loginButton = findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                co = 1;

                final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;


                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                Call<User> call = twitterApiClient.getAccountService().verifyCredentials(true, false, true);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {

                        String email = result.data.email;
                        String username = result.data.name;
                        //String profile = result.data.profileImageUrl.replace("_normal", "");
                        //String profile = result.data.profileBackgroundImageUrl.toString();
                        String profile = result.data.profileImageUrlHttps.toString().replace("_normal", "");

                        Intent intent = new Intent(MainActivity.this,Home2.class);
                        intent.putExtra("username",username);
                        intent.putExtra("emafa",email);
                        intent.putExtra("imagefa",profile);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Do something on failure
                    }
                });

            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getBaseContext(), "Fail" , Toast.LENGTH_LONG).show();
            }
        });






        //Facebook

//        loginButtonfa.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                getUserProfile(AccessToken.getCurrentAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });

        loginButtonfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        facebookAccessToken(loginResult.getAccessToken());
                        Toast.makeText(MainActivity.this, "Facebook Login Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "Facebook Login Cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(MainActivity.this,"Facebook Login Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    private void facebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Name: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(MainActivity.this, Home.class);
                            //startActivity(intent);

                            updateUI();
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }






        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);


                callbackManager.onActivityResult(requestCode, resultCode, data);
                loginButton.onActivityResult(requestCode, resultCode, data);



    }

    private void updateUI(){
        Intent intent = new Intent(MainActivity.this, Home3.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null)
            updateUI();
    }
}
