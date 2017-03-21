package com.sadique.multinotes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView time;
    public TextView notes;

    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.name);
        time = (TextView) view.findViewById(R.id.time);
        notes = (TextView) view.findViewById(R.id.notes_id);
    }
}
