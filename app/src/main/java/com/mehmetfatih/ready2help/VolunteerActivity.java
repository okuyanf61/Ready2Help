package com.mehmetfatih.ready2help;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class VolunteerActivity extends AppCompatActivity {

    ArrayList<Task> tasks;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        Intent oldIntent = getIntent();
        RecyclerView rvTasks = (RecyclerView) findViewById(R.id.volunteerRecylerView);

        ImageView menu = findViewById(R.id.volunteerHamburger);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerActivity.this, ProfileActivity.class);
                intent.putExtras(oldIntent);
                startActivity(intent);
            }
        });

        //Load all tasks from Firebase to the ArrayList
        tasks = new ArrayList<>();
        db.collection("tasks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        Task task1 = document.toObject(Task.class);
                        tasks.add(task1);

                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }

                VolunteerTaskAdapter adapter = new VolunteerTaskAdapter(tasks);
                rvTasks.setAdapter(adapter);
            }
        });
        rvTasks.setLayoutManager(new LinearLayoutManager(this));

    }
}