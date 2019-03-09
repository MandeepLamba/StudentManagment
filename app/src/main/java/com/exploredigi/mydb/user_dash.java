package com.exploredigi.mydb;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class user_dash extends AppCompatActivity {

    SQLiteDatabase database;


    TextView name,roll,marks;

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash);
        name = (TextView) findViewById(R.id.student_name2);
        roll = (TextView) findViewById(R.id.rollno2);
        marks = (TextView) findViewById(R.id.marks2);
        database = openOrCreateDatabase("login",Context.MODE_PRIVATE,null);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String roll_number = (String) bundle.get("roll");
        try{
            cursor = database.rawQuery("select * from student where roll="+roll_number+"", null);
            if (cursor.getCount() == 0) {
                Snackbar.make(findViewById(R.id.userlayout), "Not Results", Snackbar.LENGTH_SHORT).show();
            }
            else {
                cursor.moveToFirst();
                Snackbar.make(findViewById(R.id.userlayout), "Welcome "+ cursor.getString(0) , Snackbar.LENGTH_SHORT).show();
                name.setText(cursor.getString(0));
                roll.setText(cursor.getString(1));
                marks.setText(cursor.getString(2));
            }
        }
        catch (Exception e){
            Snackbar.make(findViewById(R.id.userlayout), e.toString(), Snackbar.LENGTH_SHORT).show();
        }

    }
}
