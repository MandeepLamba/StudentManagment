package com.exploredigi.mydb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class admin_dash extends AppCompatActivity implements View.OnClickListener {

    Button save,view_record;

    SQLiteDatabase database;

    EditText s_name,s_roll,s_marks;

    Button button_prev,button_next,button_delete;

    boolean isviewingrecord = false;

    String TAG = "gitar";

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        s_name = (EditText) findViewById(R.id.student_name);
        s_roll = (EditText) findViewById(R.id.rollno);
        s_marks = (EditText) findViewById(R.id.marks2);

        save = (Button) findViewById(R.id.save_button);
        view_record = (Button) findViewById(R.id.view_record);

        button_prev = (Button) findViewById(R.id.prev_button);
        button_next = (Button) findViewById(R.id.next_button);
        button_delete = (Button) findViewById(R.id.delete_button);

        database = openOrCreateDatabase("login",Context.MODE_PRIVATE,null);
        save.setOnClickListener(this);
        view_record.setOnClickListener(this);
        button_next.setOnClickListener(this);
        button_prev.setOnClickListener(this);
        button_delete.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_button:{
                if(s_name.getText().toString().trim().length() > 4){

                    if(s_roll.getText().toString().trim().length() == 4){

                        if(Integer.parseInt(s_marks.getText().toString()) <= 100 && Integer.parseInt(s_marks.getText().toString()) >= 0){
                            Cursor c = database.rawQuery("Select * from student where roll='"+Integer.parseInt(s_roll.getText().toString())+"'",null);

                            if(c.getCount()==0){
                                try{
                                    database.execSQL("INSERT INTO student VALUES('"
                                            + s_name.getText().toString().trim() + "',"
                                            + Integer.parseInt(s_roll.getText().toString()) + ","
                                            + Integer.parseInt(s_marks.getText().toString()) + ")");
                                Snackbar.make(findViewById(R.id.admin_dash),"Data Inserting",Snackbar.LENGTH_SHORT).show();
                                clear();
                                }
                                catch (Exception e){
                                    Snackbar.make(findViewById(R.id.admin_dash),"Failed to Insert data. Exception: "+ e.toString(),Snackbar.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Snackbar.make(findViewById(R.id.admin_dash),"Record Already exist",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Snackbar.make(findViewById(R.id.admin_dash),"Marks must be must be 0-100",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Snackbar.make(findViewById(R.id.admin_dash),"Roll No's must be in 4 digit",Snackbar.LENGTH_SHORT).show();
                    }
                }
                else{
                    Snackbar.make(findViewById(R.id.admin_dash),"Name's length must be >4",Snackbar.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.view_record:{
                if(isviewingrecord){
                    clear();
                    button_prev.setVisibility(View.INVISIBLE);
                    button_next.setVisibility(View.INVISIBLE);
                    button_delete.setVisibility(View.INVISIBLE);
                    save.setVisibility(View.VISIBLE);
                    isviewingrecord=false;
                    view_record.setText("View Record");
                    button_prev.setClickable(true);
                    button_next.setClickable(true);

                }
                else{
                    cursor = database.rawQuery("select * from student", null);
                    if(cursor.getCount()==0){
                        Snackbar.make(findViewById(R.id.admin_dash),"No Records found.",Snackbar.LENGTH_SHORT).show();
                    }
                    else{

                        Snackbar.make(findViewById(R.id.admin_dash),"Total " + cursor.getCount() + " record found.",Snackbar.LENGTH_SHORT).show();
                        cursor.moveToFirst();
                        s_name.setText(cursor.getString(0));
                        s_roll.setText(cursor.getString(1));
                        s_marks.setText(cursor.getString(2));
                        button_prev.setVisibility(View.VISIBLE);
                        button_next.setVisibility(View.VISIBLE);
                        button_delete.setVisibility(View.VISIBLE);
                        save.setVisibility(View.INVISIBLE);
                        isviewingrecord=true;
                        view_record.setText("Close");
                        if(cursor.isFirst()){
                            button_prev.setClickable(false);
                        }
                        if(cursor.isLast()){
                            button_next.setClickable(false);
                        }
                    }
                }
            }
            break;
            case R.id.prev_button:{
                if(cursor.isFirst()){
                    button_prev.setClickable(false);
                    Snackbar.make(findViewById(R.id.admin_dash),"First Record",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    cursor.moveToPrevious();
                    s_name.setText(cursor.getString(0));
                    s_roll.setText(cursor.getString(1));
                    s_marks.setText(cursor.getString(2));
                    button_next.setClickable(true);

                }
            }
            break;
            case R.id.next_button:{
                if(cursor.isLast()){
                    button_next.setClickable(false);
                    Snackbar.make(findViewById(R.id.admin_dash),"Last Record",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    cursor.moveToNext();
                    s_name.setText(cursor.getString(0));
                    s_roll.setText(cursor.getString(1));
                    s_marks.setText(cursor.getString(2));
                    button_prev.setClickable(true);

                }
            }
            break;
            case R.id.delete_button:{
                if(cursor.getPosition()<0){
                    button_next.setClickable(false);
                    Snackbar.make(findViewById(R.id.admin_dash),"No Record to delete",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    try{
                        database.execSQL("delete from student where roll = " + cursor.getString(1));
                        cursor = database.rawQuery("select * from student", null);
                        Snackbar.make(findViewById(R.id.admin_dash),"Data Deleting",Snackbar.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Snackbar.make(findViewById(R.id.admin_dash),"Failed to Delete data. Exception: "+ e.toString(),Snackbar.LENGTH_SHORT).show();
                    }
                    cursor.moveToFirst();
                    s_name.setText(cursor.getString(0));
                    s_roll.setText(cursor.getString(1));
                    s_marks.setText(cursor.getString(2));
                    button_next.setClickable(true);
                }
            }
            break;
        }
    }

    private void clear() {
        s_marks.setText("");
        s_roll.setText("");
        s_name.setText("");
    }
}
