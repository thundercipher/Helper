package com.tanay.thundercipher.helper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentProfileActivity extends AppCompatActivity {

    TextView nameTextView, rollNumberTextView, hostelTextView, phoneTextView;
    ImageView profilePicImageView, updatePhotoImageView;
    FirebaseDatabase database;
    DatabaseReference reference;

    public void edit(View view)
    {
        Intent i = new Intent(getApplicationContext(), StudentEditActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);

        nameTextView = (TextView)findViewById(R.id.nameTextView);
        rollNumberTextView = (TextView)findViewById(R.id.rollNumberTextView);
        hostelTextView = (TextView)findViewById(R.id.hostelTextView);
        profilePicImageView = (ImageView)findViewById(R.id.profilePicImageView);
        phoneTextView = (TextView)findViewById(R.id.phoneTextView);
        updatePhotoImageView = (ImageView)findViewById(R.id.updatePhotoimageView);

        //firestore = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Student");

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    if(snap.hasChild("Name"))
                    {
                        nameTextView.setText(snap.child("Name").getValue().toString());
                    }

                    else if(snap.hasChild("Roll Number"))
                    {
                        rollNumberTextView.setText(snap.child("Roll Number").getValue().toString());
                    }

                    else if(snap.hasChild("Hostel"))
                    {
                        hostelTextView.setText(snap.child("Hostel").getValue().toString());
                    }

                    else if(snap.hasChild("Phone Number"))
                    {
                        phoneTextView.setText(snap.child("Phone Number").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(getApplicationContext(), "Failed to retrieve information!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

/*if(!flag)
        {
            Toast.makeText(getApplicationContext(), "I'm running!", Toast.LENGTH_SHORT).show();

            firestore.collection("Users").document("Students").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task)
                {
                    DocumentSnapshot doc = task.getResult();

                    if (doc != null)
                    {
                        nameTextView.setText(doc.get("Name").toString());
                        rollNumberTextView.setText(doc.get("Roll Number").toString());
                        hostelTextView.setText(doc.get("Hostel").toString());
                        phoneTextView.setText(doc.get("Phone Number").toString());
                    }
                }
            });
        }
        */