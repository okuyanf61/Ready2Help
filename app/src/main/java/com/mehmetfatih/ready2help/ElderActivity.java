package com.mehmetfatih.ready2help;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ElderActivity extends AppCompatActivity {

    ArrayList<Task> tasks;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FloatingActionButton addTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elder);
        Intent oldIntent = getIntent();
        RecyclerView rvTasks = (RecyclerView) findViewById(R.id.elderRecylerView);

        //Load all tasks from Firebase to the ArrayList
        tasks = new ArrayList<>();
        db.collection("tasks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        Task task1 = document.toObject(Task.class);
                        String owner = oldIntent.getStringExtra("owner");
                        if (Objects.equals(task1.getTaskOwner(), owner)) {
                            tasks.add(task1);
                        }

                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }

                ElderTaskAdapter adapter = new ElderTaskAdapter(tasks);
                rvTasks.setAdapter(adapter);
            }
        });
        rvTasks.setLayoutManager(new LinearLayoutManager(this));

        ImageView menu = findViewById(R.id.elderHamburger);
        menu.setOnClickListener(v -> {
            Intent intent = new Intent(ElderActivity.this, ProfileActivity.class);
            intent.putExtras(oldIntent);
            startActivity(intent);
        });

        addTask = findViewById(R.id.elderAddTask);
        addTask.setOnClickListener(v -> {
            Intent intent = new Intent(ElderActivity.this, AddTaskActivity.class);
            intent.putExtras(oldIntent);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Task task = new Task(
                        data.getStringExtra("taskName"),
                        data.getStringExtra("taskDescription"),
                        data.getStringExtra("taskDate"),
                        data.getStringExtra("taskPhone"),
                        data.getStringExtra("taskAddress"),
                        data.getStringExtra("taskStatus"),
                        data.getStringExtra("taskOwner")
                );
                tasks.add(task);
                ElderTaskAdapter adapter = new ElderTaskAdapter(tasks);
                RecyclerView rvTasks = (RecyclerView) findViewById(R.id.elderRecylerView);
                rvTasks.setAdapter(adapter);
            }
        }
    }

}