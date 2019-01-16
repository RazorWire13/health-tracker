package com.dmuench.healthtracker;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// Code resourced from: http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Exercises> exercises;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is a string
        public View layout;
        public TextView exerciseTitle;
        public TextView exerciseDescription;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            exerciseTitle = (TextView) v.findViewById(R.id.firstLine);
            exerciseDescription = (TextView) v.findViewById(R.id.secondLine);
        }
    }

//    public void add(int position, String item) {
//        exercises.add(position, item);
//        notifyItemInserted(position);
//    }
//
//    public void remove(int position) {
//        exercises.remove(position);
//        notifyItemRemoved(position);
//    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Exercises> myDataset) {
        exercises = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.exerciseTitle.setText(exercises.get(position).getExerciseTitle());
        holder.exerciseDescription.setText(exercises.get(position).getExerciseDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return exercises.size();
    }
}
