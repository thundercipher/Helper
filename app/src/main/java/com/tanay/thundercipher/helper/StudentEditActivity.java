package com.tanay.thundercipher.helper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class StudentEditActivity extends AppCompatActivity {

    EditText nameEditText, rollNumberEditText, hostelEditText, phoneEditText;
    Button saveButton;
    FirebaseFirestore firestore;
    String name, roll, hostel, phone;
    boolean field1 = false;
    boolean field2 = false;
    boolean field3 = false;
    boolean field4 = false;

    public void navigateBack(boolean f1, boolean f2, boolean f3, boolean f4)
    {
        if(f1 && f2 && f3 && f4)
        {
            Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), StudentProfileActivity.class);
            startActivity(i);
        }

        else
        {
            Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void saveChanges(View view)
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

        firestore.collection("Users").document("Students").set(studentData, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    Intent i = new Intent(getApplicationContext(), StudentProfileActivity.class);
                    startActivity(i);
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Couldn't update your profile! Try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
     */

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
        firestore = FirebaseFirestore.getInstance();

        /*name = StudentProfileActivity.nameTextView.getText().toString();
        roll = StudentProfileActivity.rollNumberTextView.getText().toString();
        hostel = StudentProfileActivity.hostelTextView.getText().toString();
        phone = StudentProfileActivity.phoneTextView.getText().toString();
         */

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

                /*firestore.collection("Users").document("Students").set(studentData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Intent i = new Intent(getApplicationContext(), StudentProfileActivity.class);
                            startActivity(i);
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(), "Couldn't update your profile! Try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                */

                DocumentReference reference = firestore.collection("Users").document("Students");

                reference
                        .update("Name", name)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                field1 = true;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getApplicationContext(), "Failed. Try again!", Toast.LENGTH_SHORT).show();
                            }
                        });

                reference
                        .update("Roll Number", roll)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                field2 = true;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getApplicationContext(), "Failed. Try again!", Toast.LENGTH_SHORT).show();
                            }
                        });

                reference
                        .update("Hostel", hostel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                field3 = true;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getApplicationContext(), "Failed. Try again!", Toast.LENGTH_SHORT).show();
                            }
                        });

                reference
                        .update("Phone Number", phone)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                field4 = true;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getApplicationContext(), "Failed. Try again!", Toast.LENGTH_SHORT).show();
                            }
                        });

                navigateBack(field1, field2, field3, field4);
            }
        });
    }
}