package com.example.mipt4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NotesDatabaseHelper notesDatabaseHelper;
    private ArrayAdapter<String> notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // This sets the toolbar as the app's action bar

        Log.d("MainActivity", "onCreate called");

        // Initialize the ListView and Database Helper
        ListView notesListView = findViewById(R.id.notesListView);
        notesDatabaseHelper = new NotesDatabaseHelper(this);

        // Load notes from the database and set them in the ListView
        ArrayList<String> notesList = notesDatabaseHelper.getAllNotes();
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        notesListView.setAdapter(notesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the notes list when the activity is resumed
        ArrayList<String> notesList = notesDatabaseHelper.getAllNotes();
        notesAdapter.clear();
        notesAdapter.addAll(notesList);
        notesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu (create the options menu)
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item clicks from the menu
        int id = item.getItemId();
        if (id == R.id.action_add_note) {
            Log.d("MainActivity", "Create Note selected");
            // Launch AddNoteActivity when "Create Note" is selected
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        } else if (id == R.id.action_delete_note) {
            Log.d("MainActivity", "Delete Note selected");
            // Launch DeleteNoteActivity when "Delete Note" is selected
            startActivity(new Intent(this, DeleteNoteActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
