package com.example.mipt4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteNoteActivity extends AppCompatActivity {
    private Spinner spinnerNotes;
    private NotesDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        dbHelper = new NotesDatabaseHelper(this);
        spinnerNotes = findViewById(R.id.spinnerNotes);

        Log.d("DeleteNoteActivity", "Initialized DeleteNoteActivity");

        loadNotesIntoSpinner();

        findViewById(R.id.btnDeleteNote).setOnClickListener(view -> showDeleteConfirmationDialog());
    }

    private void loadNotesIntoSpinner() {
        ArrayList<String> noteTitles = dbHelper.getAllNoteTitles();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, noteTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNotes.setAdapter(adapter);
    }

    private void showDeleteConfirmationDialog() {
        String selectedTitle = (String) spinnerNotes.getSelectedItem();

        // Show confirmation dialog
        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete the note titled \"" + selectedTitle + "\"?")
                .setPositiveButton("Yes", (dialog, which) -> deleteNote())
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteNote() {
        String selectedTitle = (String) spinnerNotes.getSelectedItem();

        // Delete the note from the database
        dbHelper.deleteNoteByTitle(selectedTitle);
        Log.d("DeleteNoteActivity", "Deleted note with title: " + selectedTitle);

        // Refresh the spinner in case there are more notes to delete
        loadNotesIntoSpinner();

        // Navigate back to the main menu
        Intent intent = new Intent(this, MainActivity.class); // Replace MainActivity with your main menu activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Close DeleteNoteActivity
    }

    @Override
    public void onBackPressed() {
        // Navigate back to the main menu if the back button is pressed
        Intent intent = new Intent(this, MainActivity.class); // Replace MainActivity with your main menu activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed(); // Call super here
        finish();
    }
}
