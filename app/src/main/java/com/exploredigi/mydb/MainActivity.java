package com.exploredigi.mydb;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase database;

    EditText username_EditText, password_EditText;

    TextView register_new;
    Button login_button, check_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = openOrCreateDatabase("login", Context.MODE_PRIVATE, null);
        database.execSQL("create table if not exists student(name varchar,roll integer, marks integer)");
        database.execSQL("create table if not exists teacher(name varchar,password varchar)");
        username_EditText = (EditText) findViewById(R.id.username);
        password_EditText = (EditText) findViewById(R.id.password);
        login_button = (Button) findViewById(R.id.login_button);
        check_result = (Button) findViewById(R.id.check_result);
        register_new = (TextView) findViewById(R.id.new_teacher);
        login_button.setOnClickListener(this);
        check_result.setOnClickListener(this);
        register_new.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button: {
                username_EditText.setHint("Username");
                password_EditText.setHint("Password");
                StringBuffer name = new StringBuffer();
                StringBuffer pass = new StringBuffer();

                if (username_EditText.getVisibility() == View.VISIBLE) {
                    if (check()) {
                        try {
                            Cursor cursor = database.rawQuery("select * from teacher where name='" + username_EditText.getText().toString().trim() + "' and password='" + password_EditText.getText().toString().trim() + "'", null);
                            if (cursor.getCount() == 0) {
                                Snackbar.make(findViewById(R.id.mainlayout), "Check Username and Password", Snackbar.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(MainActivity.this, admin_dash.class));
                            }
                        } catch (Exception e) {
                            Snackbar.make(findViewById(R.id.mainlayout), e.toString(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    username_EditText.setVisibility(View.VISIBLE);
                    password_EditText.setVisibility(View.VISIBLE);
                }
            }
            break;

            case R.id.check_result: {
                username_EditText.setHint("Name");
                password_EditText.setHint("Roll No");
                username_EditText.setVisibility(View.VISIBLE);
                password_EditText.setVisibility(View.VISIBLE);
                if (username_EditText.getVisibility() == View.VISIBLE) {

                    try {
                        Cursor cursor = database.rawQuery("select * from student where name='" + username_EditText.getText().toString().trim() + "' and roll='" +password_EditText.getText().toString()+"'", null);
                        if (cursor.getCount() == 0) {
                            Snackbar.make(findViewById(R.id.mainlayout), "Check Name and Roll No.", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, user_dash.class);
                            intent.putExtra("roll",password_EditText.getText().toString().trim());
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        Snackbar.make(findViewById(R.id.mainlayout), e.toString(), Snackbar.LENGTH_SHORT).show();
                    }

                }
            }
            break;
            case R.id.new_teacher:{
                startActivity(new Intent(MainActivity.this,ragister.class));
            }
            break;
        }
    }


    private boolean check() {
        if (username_EditText.getText().toString().trim().length() > 4 && username_EditText.getText().toString().trim().length() < 13) {
            if (password_EditText.getText().toString().trim().length() > 4 && password_EditText.getText().toString().trim().length() < 10) {
                return true;
            } else {
                Snackbar.make(findViewById(R.id.mainlayout), "Password's lenght must be 5-9", Snackbar.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Snackbar.make(findViewById(R.id.mainlayout), "Username lenght must be in 5-12", Snackbar.LENGTH_SHORT).show();
            return false;
        }
    }

}
