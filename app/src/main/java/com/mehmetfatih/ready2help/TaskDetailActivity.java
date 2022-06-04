package com.mehmetfatih.ready2help;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class TaskDetailActivity extends AppCompatActivity {

    private Person owner;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent intent = getIntent();
        //{"taskAddress":"https://goo.gl/maps/7LvdVLFFh9xKqw4R9","taskDate":"04.06.2022","taskDescription":"Param yok aga\nanlayn iste ","taskName":"Acil nakit ihtiyacim var. Yardimci olacak birini ariyorum.","taskOwner":"emre@emre.com","taskPhone":"+905557790011","taskStatus":"Waiting"}
        Gson gson = new Gson();
        Task mtask = gson.fromJson(intent.getStringExtra("task"), Task.class);

        // Get owner info from Firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("people").document(mtask.getTaskOwner());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        owner = document.toObject(Person.class);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }

                TextView ownerName = findViewById(R.id.taskDetailOwnerName);
                TextView ownerPhone = findViewById(R.id.taskDetailTaskPhone);
                Log.d("TAG", "owner: " + owner.toString());
                boolean isElder = owner.isElder();
                TextView taskName = findViewById(R.id.taskDetailTaskName);
                TextView taskDescription = findViewById(R.id.taskDetailTaskDescription);
                TextView taskDate = findViewById(R.id.taskDetailTaskDate);
                ImageView taskLocation = findViewById(R.id.taskDetailTaskLocation);
                TextView taskStatus = findViewById(R.id.taskDetailStatus);

                if (Objects.equals(mtask.getTaskStatus(), "Waiting")) {
                    Log.d("TAG", owner.toString());
                    if (isElder) {
                        taskStatus.setText("Waiting for someone to take task");
                    } else {
                        taskStatus.setText("Take task");
                        taskStatus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO action of taking task
                            }
                        });
                    }
                } else {
                    if (isElder) {
                        taskStatus.setText("Task is taken");
                    } else {
                        taskStatus.setText("This task assigned to you.");
                    }
                }

                String user_type = "";
                if (intent.getStringExtra("user_type") == null) {
                    user_type = "";
                } else {
                    user_type = intent.getStringExtra("user_type");
                }


                if (user_type.equals("volunteer")) {
                    if (Objects.equals(mtask.getTaskStatus(), "Waiting")) {

                        taskStatus.setText("Take task");
                        taskStatus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                changeTaskStatus("Taken", mtask.getTaskName());
                                Log.d("TASK ADDED", "TASK ADDED");
                                Intent newIntent = new Intent(TaskDetailActivity.this, VolunteerActivity.class);
                                newIntent.putExtras(intent);
                                startActivity(newIntent);
                            }
                        });
                    } else {
                        taskStatus.setText("This task is taken.");
                    }
                }


                String[] splited = owner.getName().split("\\s+");
                String firstName = splited[0];
                String lastName = splited[1];
                ownerName.setText(firstName + " " + lastName.substring(0, 1) + ".");
                ownerPhone.setText(owner.getPhoneNumber());
                taskName.setText(mtask.getTaskName());
                taskDescription.setText(mtask.getTaskDescription());
                Date date = new Date();
                try {
                    date = new SimpleDateFormat("dd.MM.yyyy").parse(mtask.getTaskDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat df = new SimpleDateFormat("EEEE");
                String dayOfTheWeek = df.format(date);
                taskDate.setText(dayOfTheWeek.substring(0, 3));
                taskLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                android.net.Uri.parse(mtask.getTaskAddress()));
                        startActivity(intent);
                    }
                });

            }
        });
    }

    private void changeTaskStatus(String status, String taskName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("tasks").document(taskName);
        docRef.update("taskStatus", status);
    }

}