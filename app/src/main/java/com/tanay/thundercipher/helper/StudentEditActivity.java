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
    FirebaseFirestore firestore;
    FirebaseDatabase database;
    DatabaseReference reference;
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
        firestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Student");

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

               reference.child("Users").child("Student").updateChildren(studentData).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task)
                   {
                       if(task.isSuccessful())
                       {
                           Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();

                           StudentProfileActivity.nameTextView.setText(name);
                           StudentProfileActivity.rollNumberTextView.setText(roll);
                           StudentProfileActivity.hostelTextView.setText(hostel);
                           StudentProfileActivity.phoneTextView.setText(phone);

                           Intent i = new Intent(getApplicationContext(), StudentProfileActivity.class);
                           startActivity(i);
                       }

                       else
                       {
                           Toast.makeText(getApplicationContext(), "Failed! Try again", Toast.LENGTH_SHORT).show();
                       }
                   }
               });

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

                /*DocumentReference reference = firestore.collection("Users").document("Students");

                reference
                        .update("Name", name)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                //field1 = true;
                                StudentProfileActivity.nameTextView.setText(name);
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
                                //field2 = true;
                                StudentProfileActivity.rollNumberTextView.setText(roll);
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
                                //field3 = true;
                                StudentProfileActivity.hostelTextView.setText(hostel);
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
                                //field4 = true;
                                StudentProfileActivity.phoneTextView.setText(phone);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getApplicationContext(), "Failed. Try again!", Toast.LENGTH_SHORT).show();
                            }
                        });
                 */
            }
        });
    }
}