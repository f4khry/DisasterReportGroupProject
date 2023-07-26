package com.example.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button backButton = findViewById(R.id.button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to SecondActivity
                Intent intent = new Intent(About.this, SecondActivity.class);
                startActivity(intent);
                finish(); // Close the About activity to prevent going back to it using the back button
            }
        });

        TextView hyperlinkTextView = findViewById(R.id.git);
        hyperlinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite();
            }
        });
    }
    private void openWebsite() {
        String url = "https://github.com/f4khry/DisasterReportGroupProject"; // Replace with your desired URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}