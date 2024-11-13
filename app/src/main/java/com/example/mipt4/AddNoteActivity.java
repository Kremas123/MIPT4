package com.example.mipt4;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {
    private EditText txtNoteTitle, txtNoteContent;
    private NotesDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        dbHelper = new NotesDatabaseHelper(this);
        txtNoteTitle = findViewById(R.id.txtNoteTitle);
        txtNoteContent = findViewById(R.id.txtNoteContent);

        Log.d("AddNoteActivity", "Initialized AddNoteActivity");

        findViewById(R.id.btnSaveNote).setOnClickListener(view -> saveNote());
    }

    private void saveNote() {
        String title = txtNoteTitle.getText().toString().trim();
        String content = txtNoteContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Both title and content are required.", Toast.LENGTH_SHORT).show();
            Log.d("AddNoteActivity", "Failed to save note - Empty fields");
        } else {
            dbHelper.insertNote(title, content);
            Log.d("AddNoteActivity", "Note saved with title: " + title);
            finish(); // Close activity and return to MainActivity
        }
    }
}
