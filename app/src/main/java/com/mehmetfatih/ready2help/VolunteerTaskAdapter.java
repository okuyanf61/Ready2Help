package com.mehmetfatih.ready2help;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VolunteerTaskAdapter extends RecyclerView.Adapter<VolunteerTaskAdapter.ViewHolder> {

    private Person owner;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView taskImageImageView;
        public TextView taskNameTextView;
        public TextView taskTitleTextView;
        public TextView taskDateTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            taskImageImageView = itemView.findViewById(R.id.VolunteerTaskItemIcon);
            taskNameTextView = itemView.findViewById(R.id.VolunteerTaskItemName);
            taskTitleTextView = itemView.findViewById(R.id.VolunteerTaskItemTitle);
            taskDateTextView = itemView.findViewById(R.id.VolunteerTaskItemDate);

        }
    }

    // Store a member variable for the contacts
    private List<Task> mVolunteerTasks;

    // Pass in the contact array into the constructor
    public VolunteerTaskAdapter(List<Task> tasks) {
        mVolunteerTasks = tasks;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public VolunteerTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_volunteer_task, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(VolunteerTaskAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Task mtask = mVolunteerTasks.get(position);
        Log.d("TAG", "Task: " + mtask.toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("people").document(mtask.getTaskOwner());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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

                // Set item views based on your views and data model
                TextView textView = holder.taskNameTextView;
                String[] splited = owner.getName().split("\\s+");
                String firstName = splited[0];
                String lastName = splited[1];
                textView.setText(firstName + " " + lastName.substring(0, 1) + ".");
                TextView textView2 = holder.taskDateTextView;
                Date date = new Date();
                try {
                    date = new SimpleDateFormat("dd.MM.yyyy").parse(mtask.getTaskDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat df = new SimpleDateFormat("EEEE");
                String dayOfTheWeek = df.format(date);
                textView2.setText(dayOfTheWeek.substring(0, 3));
                ImageView imageView = holder.taskImageImageView;
                imageView.setImageResource(R.drawable.ic_no_profile_picture);
                TextView textView3 = holder.taskTitleTextView;
                textView3.setText(mtask.getTaskName());

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), TaskDetailActivity.class);
                        intent.putExtra("task", mtask.toString());
                        intent.putExtra("user_type", "volunteer");
                        view.getContext().startActivity(intent);
                    }
                });

                textView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), TaskDetailActivity.class);
                        intent.putExtra("task", mtask.toString());
                        intent.putExtra("user_type", "volunteer");
                        view.getContext().startActivity(intent);
                    }
                });

            }
        });

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mVolunteerTasks.size();
    }

}