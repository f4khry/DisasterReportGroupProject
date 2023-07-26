package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.news.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {


    Spinner damageTypeSpinner;
    EditText damageDescriptionEditText;
    Button submitReportButton;

    FirebaseAuth auth;
    FirebaseDatabase database;

    GoogleSignInClient nGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        damageTypeSpinner = findViewById(R.id.damageTypeSpinner);
        damageDescriptionEditText = findViewById(R.id.damageDescriptionEditText);
        submitReportButton = findViewById(R.id.submitReportButton);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        nGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Populate the Spinner with damage types
        List<String> damageTypes = new ArrayList<>();
        damageTypes.add("Flood");
        damageTypes.add("Road");
        damageTypes.add("Infrastructure");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, damageTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        damageTypeSpinner.setAdapter(adapter);

        submitReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the method to handle the report submission
                submitReport();
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to SecondActivity
                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                startActivity(intent);
                finish(); // Close the ThirdActivity to prevent going back to it using the back button
            }
        });
    }
    private void submitReport() {
        // Get the selected damage type and the damage description
        String damageType = damageTypeSpinner.getSelectedItem().toString();
        String description = damageDescriptionEditText.getText().toString().trim();

        if (description.isEmpty()) {
            // Show a toast message indicating that the description is required
            Toast.makeText(this, "Please enter a description before submitting the report.", Toast.LENGTH_SHORT).show();
            return; // Stop the report submission process
        }
        // Get the currently logged-in user's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            // Create a reference to the "Reports" node in the database
            DatabaseReference reportsRef = database.getReference("Reports");

            // Create a unique key for the report in Firebase
            String reportId = reportsRef.push().getKey();

            // Create a Map to store the report data
            Map<String, Object> reportData = new HashMap<>();
            reportData.put("email", userEmail);
            reportData.put("damageType", damageType);
            reportData.put("description", description);

            // Save the report data to Firebase Realtime Database
            reportsRef.child(reportId).setValue(reportData)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Report submission successful
                                Toast.makeText(ThirdActivity.this, "Report submitted successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Report submission failed
                                Toast.makeText(ThirdActivity.this, "Failed to submit report. Please try again.", Toast.LENGTH_SHORT).show();
                                Log.e("SubmitReport", "Error submitting report: " + task.getException());
                            }
                        }
                    });
        }
    }


}