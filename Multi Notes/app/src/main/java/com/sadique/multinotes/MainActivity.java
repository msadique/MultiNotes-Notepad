package com.sadique.multinotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final int B_REQ = 1;
    private List<QuickNotes> qnotesList = new ArrayList<>();  // Main content is here
    private RecyclerView recyclerView; // Layout's recyclerview
    private QnotesAdapter mAdapter; // Data to recyclerview adapter
    public QuickNotes qNotes, qNotes1;
    private int notepadSize = 0;
    private static final String TAG = "notepad";
    JsonData json = new JsonData();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new QnotesAdapter(qnotesList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QnotesAdapter(qnotesList, this);
        qNotes1 = new QuickNotes(1);
        qnotesList.clear();
        // Load the data
        new AsyncNotePadLoader(this).execute();
    }

    @Override
    protected void onResume() {
        super.onStart();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onPause() {
        json.writeNotepad(qnotesList, this);
        Collections.sort(qnotesList, json.notePadComparator);
        recyclerView.setAdapter(mAdapter);
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuA:
                notepadSize++;
                QuickNotes qNoteAdd = new QuickNotes(notepadSize);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(QuickNotes.class.getName(), qNoteAdd);
                startActivityForResult(intent, B_REQ);
                return true;
            case R.id.menuB:
                Intent intent1 = new Intent(MainActivity.this, NotePadAbout.class);
                startActivity(intent1);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        QuickNotes m = qnotesList.get(pos);
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra(QuickNotes.class.getName(), m);
        startActivityForResult(intent, B_REQ);

    }

    @Override
    public boolean onLongClick(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        click1(pos);
        return true;
    }

    public void updateData(ArrayList<QuickNotes> cList) {
        qnotesList.clear();
        qnotesList.addAll(cList);
        for(int i=0;i<qnotesList.size();i++)
            if(notepadSize<qnotesList.get(i).getID())
                notepadSize = qnotesList.get(i).getID();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == B_REQ ) {
            if ( resultCode == RESULT_OK ) {
                QuickNotes qnote = (QuickNotes) data.getSerializableExtra("USER_TEXT");
                if (qnote!= null) {
                    if (!qnote.getTitle().equals("")) {
                        int flag = 0;
                        for (int i = 0; i < qnotesList.size(); i++) {
                            if (qnote.getID() == qnotesList.get(i).getID()) {
                                qnotesList.get(i).setTitle(qnote.getTitle());
                                qnotesList.get(i).setTime(qnote.getTime());
                                qnotesList.get(i).setQuickNotes(qnote.getQuickNotes());
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            qnotesList.add(qnote);

                        }
                        Collections.sort(qnotesList, json.notePadComparator);
                        json.writeNotepad(qnotesList, this);
                    }
                    else
                        Toast.makeText(this, "Note: Un-titled Activity  Not Saved.", LENGTH_SHORT).show();

                }
                recyclerView.setAdapter(mAdapter);
            } else {
                Log.d(TAG, "onActivityResult: result Code: " + resultCode);
            }
        } else {
            Log.d(TAG, "onActivityResult: Request Code " + requestCode);
        }
    }
    public void click1(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.delete);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(TAG, "onActivityResult: yes  ");
                qnotesList.remove(pos);
                recyclerView.setAdapter(mAdapter);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        QuickNotes note = qnotesList.get(pos);
        builder.setTitle("Delete note '"+note.getTitle()+"'");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
