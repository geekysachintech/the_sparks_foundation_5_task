package com.example.thesparksfoundation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home3 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnLogout;

    TextView mname;
    TextView memail;
    ImageView imageView;
    private String photo_url;
    private String name;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home3);


        mAuth = FirebaseAuth.getInstance();
        mname = findViewById(R.id.username);
        memail = findViewById(R.id.email);
        imageView = findViewById(R.id.imageView);

        FirebaseUser currentUser = mAuth.getCurrentUser();

        email = currentUser.getEmail();
        name = currentUser.getDisplayName();
        photo_url = currentUser.getPhotoUrl().toString();
        photo_url = photo_url + "?height=500";
//        Log.d("email", email);
        Glide.with(this).load(photo_url).into(imageView);
        mname.setText(name);
        memail.setText(email);
    }
}