package com.mehmetfatih.ready2help;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivityThree extends AppCompatActivity {

    private EditText editTextRegisterCountryCode, editTextRegisterPhoneNumber;
    private Button buttonRegisterContinue3;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_three);

        editTextRegisterCountryCode = findViewById(R.id.editTextRegisterCountryCode);
        editTextRegisterPhoneNumber = findViewById(R.id.editTextRegisterPhoneNumber);
        buttonRegisterContinue3 = findViewById(R.id.buttonRegisterContinue3);

        buttonRegisterContinue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent oldIntent = getIntent();
                String email = oldIntent.getStringExtra("email");
                String password = oldIntent.getStringExtra("password");
                boolean isElder = oldIntent.getBooleanExtra("isElder", false);
                String name = oldIntent.getStringExtra("name");
                String day = oldIntent.getStringExtra("day");
                String month = oldIntent.getStringExtra("month");
                String year = oldIntent.getStringExtra("year");
                String city = oldIntent.getStringExtra("city");
                boolean isMale = oldIntent.getBooleanExtra("isMale", false);
                String countryCode = editTextRegisterCountryCode.getText().toString();
                String phoneNumber = editTextRegisterPhoneNumber.getText().toString();

                Person person = new Person(
                        name,
                        email,
                        password,
                        day+"/"+month+"/"+year,
                        city,
                        countryCode+phoneNumber,
                        isMale,
                        isElder
                );

                Intent intent = new Intent(SignUpActivityThree.this, LoginActivity.class);

                DocumentReference docRef = db.collection("people").document(email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(SignUpActivityThree.this, "User with that email already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                db.collection("people").document(email).set(person).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("TAG", "DocumentSnapshot successfully written!");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error writing document", e);
                                    }
                                });

                                Log.i("SignUpActivityThree", "Person: " + person.toString());

                                startActivity(intent);
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
            }
        });

    }
}