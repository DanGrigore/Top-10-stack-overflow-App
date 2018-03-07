package com.example.grigo.ab4;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class Main2Activity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView biggerProfilePictureImg = findViewById(R.id.biggerProfilePictureImg);
        TextView nameTag = findViewById(R.id.nameTag);
        TextView locationTag = findViewById(R.id.locationTag);

        TextView bronzeMedals = findViewById(R.id.bronzeMedalsTag);
        TextView silverMedals = findViewById(R.id.silverMedalsTag);
        TextView goldMedals = findViewById(R.id.goldMedalsTag);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String biggerProfilePicture = bundle.getString("profilePic");

            String name = (String) bundle.get("name");
            nameTag.setText(name);

            String location = (String) bundle.get("location");
            locationTag.setText(location);

            int bronze = bundle.getInt("bronze", 0);
            int silver = bundle.getInt("silver", 0);
            int gold = bundle.getInt("gold", 0);
            bronzeMedals.setText(Integer.toString(bronze));
            silverMedals.setText(Integer.toString(silver));
            goldMedals.setText(Integer.toString(gold));

            final ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
            ImageLoader.getInstance().displayImage(biggerProfilePicture,
                    biggerProfilePictureImg, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {
                            progressBar2.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                            progressBar2.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            progressBar2.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {
                            progressBar2.setVisibility(View.GONE);
                        }
                    });

        }

    }
}
