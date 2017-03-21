package com.sadique.multinotes;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AsyncNotePadLoader extends AsyncTask<String, Integer, String>   {

    private MainActivity mainActivity;
    private int count;
    private static final String TAG = "AsyncNotePadLoader";
    JsonData jsonfile = new JsonData();

    public AsyncNotePadLoader(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(mainActivity, "Loading NotePad....", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "doInBackground: " +s);
        if( s != null ){
        ArrayList<QuickNotes> qnotesList = jsonfile.parseJSON(s);
            Collections.sort(qnotesList, jsonfile.notePadComparator);
            mainActivity.updateData(qnotesList);}
    }

    @Override
    protected String doInBackground(String... params) {

        String json = null;
        json = jsonfile.readNotes(mainActivity);
        try {
        jsonfile.readNotepad(mainActivity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
