package com.sadique.multinotes;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
/**
 * Created by sadique on 2/22/2017.
 */
public class JsonData extends AppCompatActivity {
    private static final String TAG = "notepad";

    void saveNotes(QuickNotes qNotes, EditActivity ma) {
        Log.d(TAG, "saveProduct: Saving JSON File hello");
        try {
            FileOutputStream fos = ma.getApplicationContext().openFileOutput("temppad.json", Context.MODE_PRIVATE);

            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, ma.getString(R.string.encoding)));
            writer.setIndent("  ");
            writer.beginObject();
            DateFormat df = new SimpleDateFormat("EEE MMM d, h:mm:ss a");
            String date = df.format(Calendar.getInstance().getTime());
            writer.name("ID").value(qNotes.getID());
            writer.name("Time").value(date);
            writer.name("Title").value(qNotes.getTitle());
            writer.name("Notepad").value(qNotes.getQuickNotes());
            writer.endObject();

            writer.close();

            StringWriter sw = new StringWriter();
            writer = new JsonWriter(sw);
            writer.setIndent("  ");
            writer.beginObject();
            writer.name("ID").value(qNotes.getID());
            writer.name("Title").value(qNotes.getTitle());
            writer.name("Time").value(date);
            writer.name("Notepad").value(qNotes.getQuickNotes());
            writer.endObject();
            writer.close();
            Log.d(TAG, "saveProduct: JSON:\n" + sw.toString());

          //  Toast.makeText(ma, "json data", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    QuickNotes loadFile(EditActivity ma) {
        QuickNotes qNotes;
        Log.d(TAG, "loadFile: Loading JSON File");
        qNotes = new QuickNotes(999);
        try {
            InputStream is = ma.getApplicationContext().openFileInput("temppad.json");
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("ID")) {
                    qNotes.setID(Integer.parseInt(reader.nextString()));
                } else if (name.equals("Title")) {
                    qNotes.setTitle(reader.nextString());
                }else if (name.equals("Time")) {
                    qNotes.setTime(reader.nextString());
                } else if (name.equals("Notepad")) {
                    qNotes.setQuickNotes(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();

        } catch (FileNotFoundException e) {
            Toast.makeText(ma, ma.getString(R.string.no_notes), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qNotes;
    }

    public QuickNotes loadFile(MainActivity mainActivity) {
        QuickNotes qNotes;
        Log.d(TAG, "loadFile: Loading JSON File");
        qNotes = new QuickNotes();
        try {
            InputStream is = mainActivity.getApplicationContext().openFileInput("temppad.json");
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("ID")) {
                    qNotes.setID(Integer.parseInt(reader.nextString()));
                } else if (name.equals("Title")) {
                    qNotes.setTitle(reader.nextString());
                }else if (name.equals("Time")) {
                    qNotes.setTime(reader.nextString());
                } else if (name.equals("Notepad")) {
                    qNotes.setQuickNotes(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();

        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qNotes;
    }

    public void writeNotepad(List<QuickNotes> qnotes, MainActivity ma) {
        try {
            FileOutputStream fos = ma.getApplicationContext().openFileOutput("Notepad.json", Context.MODE_PRIVATE);
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, ma.getString(R.string.encoding)));
            writer.setIndent("  ");
            writeMessagesArray(writer, qnotes);
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void writeMessagesArray(JsonWriter writer, List<QuickNotes> qnotes) throws
            IOException {
        writer.beginArray();
        for ( QuickNotes qnote :qnotes) {
            writeMessage(writer, qnote);
        }
        writer.endArray();
    }
    public void writeMessage(JsonWriter writer, QuickNotes qNote) throws IOException {
        writer.beginObject();
        writer.name("ID").value(qNote.getID());
        writer.name("Title").value(qNote.getTitle());
        writer.name("Time").value(qNote.getTime());
        writer.name("Notepad").value(qNote.getQuickNotes());
        writer.endObject();
        }
    public String readNotes(MainActivity ma){
    String json = null;
    try {
        InputStream is = ma.getApplicationContext().openFileInput("Notepad.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");
    } catch (IOException ex) {
        ex.printStackTrace();
        return null;
    }
        Log.d(TAG, json);

        return json;
    }
    public List<QuickNotes> readNotepad(MainActivity ma) throws IOException {
        InputStream is = ma.getApplicationContext().openFileInput("Notepad.json");
        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<QuickNotes> readMessagesArray(JsonReader reader) throws IOException {
        List<QuickNotes> messages = new ArrayList<QuickNotes>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    public QuickNotes readMessage(JsonReader reader) throws IOException {
       QuickNotes qNotes = new QuickNotes();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ID")) {
                qNotes.setID(Integer.parseInt(reader.nextString()));
            } else if (name.equals("Title")) {
                qNotes.setTitle(reader.nextString());
            }else if (name.equals("Time")) {
                qNotes.setTime(reader.nextString());
            } else if (name.equals("Notepad")) {
                qNotes.setQuickNotes(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return qNotes;
    }
    public ArrayList<QuickNotes> parseJSON(String s) {

        ArrayList<QuickNotes> qNotes = new ArrayList<>();
        try {
            JSONArray jObjMain = new JSONArray(s);
            for (int i = 0; i < jObjMain.length(); i++) {
                JSONObject notes = (JSONObject) jObjMain.get(i);
                int id = Integer.parseInt(notes.getString("ID"));
                String title = notes.getString("Title");
                String time = notes.getString("Time");
                String note = notes.getString("Notepad");

                qNotes.add(
                        new QuickNotes(id,title,time,note));

            }
            return qNotes;
        } catch (Exception e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public static Comparator<QuickNotes> notePadComparator = new Comparator<QuickNotes>() {

        @Override
        public int compare(QuickNotes quickNotes, QuickNotes t1) {
            //quickNotes.getTime();
            Date start = null,end=null;
            DateFormat df = new SimpleDateFormat("EEE MMM d, h:mm:ss a");
            try {
                start = new SimpleDateFormat("EEE MMM d, h:mm:ss a").parse(quickNotes.getTime());
                end = new SimpleDateFormat("EEE MMM d, h:mm:ss a").parse(t1.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "parseJSON: " + quickNotes.getTime());

            if( end.compareTo(start) == 0) {
                return (t1.getID() - quickNotes.getID());
            }
            else
                return end.compareTo(start);
        }

    };
}


