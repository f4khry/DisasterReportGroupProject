package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView reportsRecyclerView;
    private ReportAdapter reportAdapter;
    private List<Report> reportsList;

    Button signout;
    Button reportButton;

    Button About;

    private FirebaseAuth auth;
    private DatabaseReference reportsRef;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        auth = FirebaseAuth.getInstance();
        reportsRef = FirebaseDatabase.getInstance().getReference("Reports");

        reportButton = findViewById(R.id.reportButton);
        signout = findViewById(R.id.signout);
        About = findViewById((R.id.About));

        reportsRecyclerView = findViewById(R.id.reportsRecyclerView);
        reportsList = new ArrayList<>();
        reportAdapter = new ReportAdapter(this, reportsList);
        reportsRecyclerView.setAdapter(reportAdapter);
        reportsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configure Google Sign-In options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Fetch data from Firebase and display it in the RecyclerView
        fetchReportsFromFirebase();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, About.class);
                startActivity(intent);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchReportsFromFirebase() {
        reportsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reportsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Report report = snapshot.getValue(Report.class);
                    reportsList.add(report);
                }
                reportAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // This method will be called if there's an error fetching the data
                // Handle the error here (optional)
                Toast.makeText(SecondActivity.this, "Error fetching reports: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signOut() {
        // Sign out from Firebase Authentication
        auth.signOut();

        // Sign out from Google Sign-In
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                // After sign out, go back to MainActivity
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the SecondActivity to prevent going back to it after sign-out
            }
        });
    }
}
