package com.example.studentmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    private ListView lvStudents;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        dbHelper = new DatabaseHelper(this);
        lvStudents = findViewById(R.id.lvStudents);

        displayStudents();

        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = (Student) parent.getItemAtPosition(position);
                Intent intent = new Intent(StudentListActivity.this, StudentFormActivity.class);
                intent.putExtra("STUDENT_ID", student.getId());
                startActivity(intent);
            }
        });
    }

    private void displayStudents() {
        List<Student> students = dbHelper.getAllStudents();
        ArrayAdapter<Student> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, students);
        lvStudents.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayStudents();
    }
}