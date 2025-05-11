package com.example.studentmanagementsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentFormActivity extends AppCompatActivity {

    private EditText etStudentId, etFirstName, etLastName, etCourse, etGroup;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private String studentId;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        dbHelper = new DatabaseHelper(this);

        etStudentId = findViewById(R.id.etStudentId);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etCourse = findViewById(R.id.etCourse);
        etGroup = findViewById(R.id.etGroup);
        btnSave = findViewById(R.id.btnSave);

        // Check if we're updating an existing student
        if (getIntent().hasExtra("STUDENT_ID")) {
            isUpdate = true;
            studentId = getIntent().getStringExtra("STUDENT_ID");
            Student student = dbHelper.getStudent(studentId);

            etStudentId.setText(student.getId());
            etFirstName.setText(student.getFirstName());
            etLastName.setText(student.getLastName());
            etCourse.setText(student.getCourse());
            etGroup.setText(student.getGroup());

            etStudentId.setEnabled(false); // Don't allow ID change for updates
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudent();
            }
        });
    }

    private void saveStudent() {
        String id = etStudentId.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String course = etCourse.getText().toString().trim();
        String group = etGroup.getText().toString().trim();

        if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || course.isEmpty() || group.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(id, firstName, lastName, course, group);

        if (isUpdate) {
            dbHelper.updateStudent(student);
            Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.addStudent(student);
            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}