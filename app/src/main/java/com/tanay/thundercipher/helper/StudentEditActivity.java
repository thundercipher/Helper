package com.tanay.thundercipher.helper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class StudentEditActivity extends AppCompatActivity {

    EditText nameEditText, rollNumberEditText, hostelEditText, phoneEditText;
    Button saveButton;
    FirebaseDatabase database;
    String name, roll, hostel, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit);

        nameEditText = (EditText)findViewById(R.id.nameEditText);
        rollNumberEditText = (EditText)findViewById(R.id.rollNumberEditText);
        hostelEditText = (EditText)findViewById(R.id.hostelEditText);
        phoneEditText = (EditText)findViewById(R.id.phoneEditText);
        saveButton = (Button)findViewById(R.id.saveButton);
        Intent intent = getIntent();

        database = FirebaseDatabase.getInstance();

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                name = nameEditText.getText().toString();
                roll = rollNumberEditText.getText().toString();
                hostel = hostelEditText.getText().toString();
                phone = phoneEditText.getText().toString();

                Map<String, Object> studentData = new HashMap<>();
                studentData.put("Name", name);
                studentData.put("Roll Number", roll);
                studentData.put("Hostel", hostel);
                studentData.put("Phone Number", phone);

                //firestore.collection("Users").document("Students").set(studentData);
                database.getReference().child("Users").child("Student").updateChildren(studentData)

                        .addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(getApplicationContext(), StudentProfileActivity.class);
                                startActivity(i);
                            }
                        })

                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
