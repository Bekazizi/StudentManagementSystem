package com.example.studentmanagementsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearchQuery;
    private Button btnSearch;
    private ListView lvSearchResults;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dbHelper = new DatabaseHelper(this);
        etSearchQuery = findViewById(R.id.etSearchQuery);
        btnSearch = findViewById(R.id.btnSearch);
        lvSearchResults = findViewById(R.id.lvSearchResults);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearchQuery.getText().toString().trim();
                if (!query.isEmpty()) {
                    List<Student> searchResults = dbHelper.searchStudents(query);
                    ArrayAdapter<Student> adapter = new ArrayAdapter<>(SearchActivity.this,
                            android.R.layout.simple_list_item_1, searchResults);
                    lvSearchResults.setAdapter(adapter);
                }
            }
        });
    }
}