package com.mehmetfatih.ready2help;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ElderTaskAdapter extends RecyclerView.Adapter<ElderTaskAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView taskImageImageView;
        public TextView taskNameTextView;
        public TextView taskDateTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            taskImageImageView = itemView.findViewById(R.id.ElderTaskItemIcon);
            taskNameTextView = itemView.findViewById(R.id.ElderTaskItemTitle);
            taskDateTextView = itemView.findViewById(R.id.ElderTaskItemDate);

        }
    }

    // Store a member variable for the contacts
    private List<Task> mElderTasks;

    // Pass in the contact array into the constructor
    public ElderTaskAdapter(List<Task> tasks) {
        mElderTasks = tasks;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ElderTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_elder_task, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ElderTaskAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Task task = mElderTasks.get(position);
        Log.d("TAG", "Task: " + task.toString());

        // Set item views based on your views and data model
        TextView textView = holder.taskNameTextView;
        textView.setText(task.getTaskName());
        TextView textView2 = holder.taskDateTextView;
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd.MM.yyyy").parse(task.getTaskDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("EEEE");
        String dayOfTheWeek = df.format(date);
        textView2.setText(dayOfTheWeek.substring(0, 3));
        ImageView imageView = holder.taskImageImageView;
        if (Objects.equals(task.getTaskStatus(), "Waiting")) {
            imageView.setImageResource(R.mipmap.ic_waiting);
        }
        else {
            imageView.setImageResource(R.mipmap.ic_taken);
        }



    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mElderTasks.size();
    }

}