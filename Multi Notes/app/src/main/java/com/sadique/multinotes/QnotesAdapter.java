package com.sadique.multinotes;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class QnotesAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "QnotesAdapter";
    private List<QuickNotes> noteslist;
    private MainActivity mainAct;

    public QnotesAdapter(List<QuickNotes> empList, MainActivity ma) {
        this.noteslist = empList;
        mainAct = ma;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        //Log.d(TAG, "onCreateViewHolder: MAKING NEW");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        QuickNotes note1 = noteslist.get(position);
        holder.title.setText(note1.getTitle());
        holder.time.setText((note1.getTime()));
        Log.d(TAG, note1.getQuickNotes());
        if(note1.getQuickNotes().length()>80) {
            holder.notes.setText(note1.getQuickNotes().substring(0,80)+"....");
        }
        else
            holder.notes.setText(note1.getQuickNotes());

    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

}