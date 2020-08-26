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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentProfileActivity extends AppCompatActivity {

    TextView nameTextView, rollNumberTextView, hostelTextView, phoneTextView;
    ImageView profilePicImageView, updatePhotoImageView;
    FirebaseFirestore firestore;
    boolean flag = true;

    public void edit(View view)
    {
        flag = false;
        Intent i = new Intent(getApplicationContext(), StudentEditActivity.class);
        startActivity(i);
    }

    /*public void getPhoto()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    // when all permissions are granted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )
            {
                getPhoto();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null)         // to make sure user selected an image file
        {
            Uri selectedImage = data.getData();

            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);          // to retrieve the data and convert it into a Bitmap format

                //add code to upload to firebase storage and then use the uploaded file to set the photo in the imageview

                profilePicImageView.setImageBitmap(bitmap);
            }

            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
     */

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

        /*updatePhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // explicitly check for permissions
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }

                else
                {
                    getPhoto();
                }
            }
        });
         */

        firestore = FirebaseFirestore.getInstance();
        Intent i = getIntent();

        if(flag)
        {
            Map<String, Object> studentData = new HashMap<>();
            studentData.put("Name", nameTextView.getText().toString());
            studentData.put("Roll Number", rollNumberTextView.getText().toString());
            studentData.put("Hostel", hostelTextView.getText().toString());
            studentData.put("Phone Number", phoneTextView.getText().toString());

            firestore.collection("Users").document("Students").set(studentData);
        }

        if(!flag)
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
    }
}