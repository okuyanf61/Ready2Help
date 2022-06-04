package com.mehmetfatih.ready2help;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    private String pattern = "dd.MM.yyyy";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Intent oldIntent = getIntent();

        EditText taskName = findViewById(R.id.addTaskName);
        EditText taskDescription = findViewById(R.id.addTaskDescription);
        ImageView goBack = findViewById(R.id.addTaskBack);
        ImageView addTaskLocation = findViewById(R.id.addTaskLocation);
        TextView done = findViewById(R.id.addTaskDone);

        String phone = oldIntent.getStringExtra("phoneNumber");
        String sTaskName = taskName.getText().toString();

        String dateInString = new SimpleDateFormat(pattern).format(new Date());

        goBack.setOnClickListener(v -> {
            finish();
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new task object
                Task newTask = new Task(
                        taskName.getText().toString(),
                        taskDescription.getText().toString(),
                        dateInString,
                        phone,
                        "https://goo.gl/maps/7LvdVLFFh9xKqw4R9",
                        "Waiting",
                        oldIntent.getStringExtra("owner")
                );

                Intent intent = new Intent();

                Log.d("AddTaskActivity", "Task name: " + newTask.getTaskName());

                // Add task to Firebase database
                DocumentReference docRef = db.collection("tasks").document(newTask.getTaskName());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(AddTaskActivity.this, "Task with that name already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                db.collection("tasks").document(newTask.getTaskName()).set(newTask).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                                Log.i("AddTaskActivity", "Task: " + newTask.toString());
                                setResult(RESULT_OK, intent);
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                            setResult(RESULT_CANCELED, intent);
                        }
                        intent.putExtras(oldIntent);
                        intent.putExtra("taskName", newTask.getTaskName());
                        intent.putExtra("taskDescription", newTask.getTaskDescription());
                        intent.putExtra("taskDate", newTask.getTaskDate());
                        intent.putExtra("taskPhone", newTask.getTaskPhone());
                        intent.putExtra("taskAddress", newTask.getTaskAddress());
                        intent.putExtra("taskStatus", newTask.getTaskStatus());
                        intent.putExtra("taskOwner", newTask.getTaskOwner());

                        finish();
                    }
                });
            }
        });

        addTaskLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(android.net.Uri.parse("https://goo.gl/maps/7LvdVLFFh9xKqw4R9"));
                startActivity(intent);
            }
        });
    }
}