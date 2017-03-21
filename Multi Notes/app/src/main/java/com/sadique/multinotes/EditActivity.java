package com.sadique.multinotes;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "notepad";
    private EditText title;
    private EditText notepad;
    private QuickNotes qNotes,qNotes1;
    private String nChange, tChange,note_ctr;
    private EditText editText;
    JsonData json = new JsonData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        title = (EditText) findViewById(R.id.title_id);
        notepad = (EditText) findViewById(R.id.notes_id);
        notepad.setMovementMethod(new ScrollingMovementMethod());
        notepad.setTextIsSelectable(true);
        Intent intent = getIntent();
        if (intent.hasExtra(QuickNotes.class.getName())) {
                qNotes = (QuickNotes) intent.getSerializableExtra(QuickNotes.class.getName());
           }
    }
    @Override
    protected void onResume() {
        super.onStart();

        if (qNotes != null) {
            tChange = qNotes.getTitle();
            nChange = qNotes.getQuickNotes();
            title.setText(qNotes.getTitle());
            if(qNotes.getTitle().length()>0) {
                title.setSelection(qNotes.getTitle().length());
            }
            nChange = qNotes.getQuickNotes();
            notepad.setText(qNotes.getQuickNotes());
            if(qNotes.getQuickNotes().length()>0) {
                notepad.setSelection(qNotes.getQuickNotes().length());
            }
       }
    }
    @Override
    protected void onPause() {
        if(!(tChange.equals(title.getText().toString())&&nChange.equals(notepad.getText().toString()))) {
            qNotes.setTitle(title.getText().toString());
            qNotes.setQuickNotes(notepad.getText().toString());
            DateFormat df = new SimpleDateFormat("EEE MMM d, h:mm:ss a");
            String date = df.format(Calendar.getInstance().getTime());
            qNotes.setTime(date);
        }
        super.onPause();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notemenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuC:
                if(!(tChange.equals(title.getText().toString())&&nChange.equals(notepad.getText().toString()))) {
                    qNotes.setTitle(title.getText().toString());
                    qNotes.setQuickNotes(notepad.getText().toString());
                    DateFormat df = new SimpleDateFormat("EEE MMM d, h:mm:ss a");
                    String date = df.format(Calendar.getInstance().getTime());
                    qNotes.setTime(date);
                }
                Intent data = new Intent();
                data.putExtra("USER_TEXT", qNotes);
                setResult(RESULT_OK, data);
                finish();
                return true;
            case android.R.id.home:
                if(!(tChange.equals(title.getText().toString())&&nChange.equals(notepad.getText().toString()))) {
                    click1();
                }
                else
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if(!(tChange.equals(title.getText().toString())&&nChange.equals(notepad.getText().toString()))) {
            click1();
        }
        else {
            super.onBackPressed();
            finish();
        }
    }
    public void click1() {
        String str = title.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.delete);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(!(tChange.equals(title.getText().toString())&&nChange.equals(notepad.getText().toString()))) {
                    qNotes.setTitle(title.getText().toString());
                    qNotes.setQuickNotes(notepad.getText().toString());
                    DateFormat df = new SimpleDateFormat("EEE MMM d, h:mm:ss a");
                    String date = df.format(Calendar.getInstance().getTime());
                    qNotes.setTime(date);
                }
                Intent data = new Intent();
                data.putExtra("USER_TEXT", qNotes);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                QuickNotes note = null;
                Intent data = new Intent();
                data.putExtra("USER_TEXT", note);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        builder.setTitle("Your Note is not saved!");
        builder.setMessage("Save note '"+str+ "'");

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

