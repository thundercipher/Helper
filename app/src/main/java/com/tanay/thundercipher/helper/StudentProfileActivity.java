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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentProfileActivity extends AppCompatActivity {

    static TextView nameTextView, rollNumberTextView, hostelTextView, phoneTextView;
    ImageView profilePicImageView, updatePhotoImageView;
    //FirebaseFirestore firestore;
    FirebaseDatabase database;
    boolean flag = true;

    public void edit(View view)
    {
        flag = false;
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
        database = FirebaseDatabase.getInstance();
        Intent i = getIntent();

        if(flag)
        {
            Map<String, Object> studentData = new HashMap<>();
            studentData.put("Name", nameTextView.getText().toString());
            studentData.put("Roll Number", rollNumberTextView.getText().toString());
            studentData.put("Hostel", hostelTextView.getText().toString());
            studentData.put("Phone Number", phoneTextView.getText().toString());

            //firestore.collection("Users").document("Students").set(studentData);
            database.getReference().child("Users").child("Student").updateChildren(studentData);
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
    }
}