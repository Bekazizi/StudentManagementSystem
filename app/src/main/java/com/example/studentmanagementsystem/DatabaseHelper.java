// DatabaseHelper.java
package com.example.studentmanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENTS = "students";

    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_COURSE = "course";
    private static final String KEY_GROUP = "group_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_FIRST_NAME + " TEXT,"
                + KEY_LAST_NAME + " TEXT,"
                + KEY_COURSE + " TEXT,"
                + KEY_GROUP + " TEXT" + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    // Add new student
    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, student.getId());
        values.put(KEY_FIRST_NAME, student.getFirstName());
        values.put(KEY_LAST_NAME, student.getLastName());
        values.put(KEY_COURSE, student.getCourse());
        values.put(KEY_GROUP, student.getGroup());
        db.insert(TABLE_STUDENTS, null, values);
        db.close();
    }

    // Get single student
    public Student getStudent(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS,
                new String[]{KEY_ID, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_COURSE, KEY_GROUP},
                KEY_ID + "=?", new String[]{id}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Student student = new Student(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        cursor.close();
        return student;
    }

    // Get all students
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getString(0));
                student.setFirstName(cursor.getString(1));
                student.setLastName(cursor.getString(2));
                student.setCourse(cursor.getString(3));
                student.setGroup(cursor.getString(4));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentList;
    }

    // Update student
    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, student.getFirstName());
        values.put(KEY_LAST_NAME, student.getLastName());
        values.put(KEY_COURSE, student.getCourse());
        values.put(KEY_GROUP, student.getGroup());

        return db.update(TABLE_STUDENTS, values, KEY_ID + " = ?",
                new String[]{student.getId()});
    }

    // Delete student
    public void deleteStudent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, KEY_ID + " = ?", new String[]{id});
        db.close();
    }

    // Search students by name or ID
    public List<Student> searchStudents(String query) {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String searchQuery = "SELECT * FROM " + TABLE_STUDENTS + " WHERE "
                + KEY_ID + " LIKE ? OR "
                + KEY_FIRST_NAME + " LIKE ? OR "
                + KEY_LAST_NAME + " LIKE ?";

        Cursor cursor = db.rawQuery(searchQuery,
                new String[]{"%" + query + "%", "%" + query + "%", "%" + query + "%"});

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getString(0));
                student.setFirstName(cursor.getString(1));
                student.setLastName(cursor.getString(2));
                student.setCourse(cursor.getString(3));
                student.setGroup(cursor.getString(4));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentList;
    }
}