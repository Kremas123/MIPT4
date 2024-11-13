package com.example.mipt4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class NotesDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";

    public NotesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("NotesDatabaseHelper", "Database Helper initialized");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NOTES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT)";
        db.execSQL(createTable);
        Log.d("NotesDatabaseHelper", "Table created: " + TABLE_NOTES);
    }

    public void insertNote(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CONTENT, content);
        db.insert(TABLE_NOTES, null, values);
        Log.d("NotesDatabaseHelper", "Inserted note: " + title);
    }

    public ArrayList<String> getAllNotes() {
        ArrayList<String> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, null, null, null, null, null, null);

        if (cursor != null) {
            int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
            int contentIndex = cursor.getColumnIndex(COLUMN_CONTENT);

            // Check if column indices are valid
            if (titleIndex != -1 && contentIndex != -1) {
                while (cursor.moveToNext()) {
                    String title = cursor.getString(titleIndex);
                    String content = cursor.getString(contentIndex);
                    notes.add(title + ": " + content);
                }
            } else {
                Log.e("NotesDatabaseHelper", "Column index not found for title or content");
            }
            cursor.close();
        }
        return notes;
    }

    public ArrayList<String> getAllNoteTitles() {
        ArrayList<String> titles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, new String[]{COLUMN_TITLE}, null, null, null, null, null);

        if (cursor != null) {
            int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);

            // Check if the column index is valid
            if (titleIndex != -1) {
                while (cursor.moveToNext()) {
                    titles.add(cursor.getString(titleIndex));
                }
            } else {
                Log.e("NotesDatabaseHelper", "Column index not found for title");
            }
            cursor.close();
        }
        return titles;
    }

    public void deleteNoteByTitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_TITLE + "=?", new String[]{title});
        Log.d("NotesDatabaseHelper", "Deleted note: " + title);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }
}
