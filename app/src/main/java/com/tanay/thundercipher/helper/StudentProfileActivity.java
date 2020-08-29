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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentProfileActivity extends AppCompatActivity {

    TextView nameTextView, rollNumberTextView, hostelTextView, phoneTextView;
    ImageView profilePicImageView, updatePhotoImageView;
    FirebaseDatabase database;
    DatabaseReference reference;
    StorageReference mStorageRef;

    public void edit(View view)
    {
        Intent i = new Intent(getApplicationContext(), StudentEditActivity.class);
        startActivity(i);
    }

    public void getPhoto()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)             //this gets called after the function onActivityResult()
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null)         // to make sure user selected an image file
        {
            Uri imageUri = data.getData();
            //imageView.setImageURI(imageUri);

            uploadPhoto(imageUri);
        }
    }

    public void uploadPhoto(Uri imageUri)
    {
        final StorageReference photoRef = mStorageRef.child("images/profile");      // for multiple users -> child("Users/" + auth.getCurrentUser().getUid() + "/profile");

        photoRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        //imageView.setImageURI(imageUri);

                        photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri)
                            {
                                Picasso.get().load(uri).into(profilePicImageView);
                                Snackbar.make(findViewById(android.R.id.content), "Upload Successful!", Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception exception)
                    {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
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
        reference = database.getReference().child("Users").child("Student");

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    if(snap.getKey().equals("Name"))
                    {
                        nameTextView.setText(snap.getValue().toString());
                        //Log.i("Name", snap.getValue().toString());
                    }

                    else if(snap.getKey().equals("Roll Number"))
                    {
                        rollNumberTextView.setText(snap.getValue().toString());
                        //Log.i("Roll Number", snap.getValue().toString());
                    }

                    else if(snap.getKey().equals("Hostel"))
                    {
                        hostelTextView.setText(snap.getValue().toString());
                        //Log.i("Hostel", snap.getValue().toString());
                    }

                    else if(snap.getKey().equals("Phone Number"))
                    {
                        phoneTextView.setText(snap.getValue().toString());
                        //Log.i("Phone Number", snap.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(getApplicationContext(), "Failed to retrieve information!", Toast.LENGTH_SHORT).show();
            }
        });

        StorageReference finalRef = mStorageRef.child("images/profile");                    // for multiple users -> child("Users/" + auth.getCurrentUser().getUid() + "/profile");
        finalRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri)
            {
                Picasso.get().load(uri).into(profilePicImageView);
            }
        });

        updatePhotoImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getPhoto();
            }
        });
    }
}

