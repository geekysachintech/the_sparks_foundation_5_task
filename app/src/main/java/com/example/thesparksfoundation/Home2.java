package com.example.thesparksfoundation;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Home2 extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);


        String username = getIntent().getStringExtra("username");
        String imagefa = getIntent().getStringExtra("imagefa");
        String emailfa = getIntent().getStringExtra("emafa");

        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        imageView = findViewById(R.id.imageView);



      name.setText(username);


        email.setText(emailfa);

        Log.d("image", imagefa);

        Picasso.get().load(imagefa).placeholder(R.drawable.com_facebook_profile_picture_blank_square).into(imageView);


//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logout();
//            }
//        });
    }
}