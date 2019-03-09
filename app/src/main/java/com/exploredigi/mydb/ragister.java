package com.exploredigi.mydb;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.ScriptGroup;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ragister extends AppCompatActivity {

    SQLiteDatabase database;
    EditText username, password;
    Button submit,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragister);

        database = openOrCreateDatabase("login",Context.MODE_PRIVATE,null);

        username = (EditText) findViewById(R.id.t_username);
        password = (EditText) findViewById(R.id.t_password);
        submit = (Button) findViewById(R.id.register);
        cancel = (Button) findViewById(R.id.cancel);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){
                    try{
                        database.execSQL("insert into teacher values('"+username.getText().toString().trim()+"','"+password.getText().toString().trim()+"')");
                        startActivity(new Intent(ragister.this,MainActivity.class));
                    }
                    catch (Exception e){
                        Snackbar.make(findViewById(R.id.register_activity), e.toString(), Snackbar.LENGTH_SHORT).show();

                    }

                }
            }
        });
    }

    private boolean check() {
        if (username.getText().toString().trim().length() > 4 && username.getText().toString().trim().length() < 13) {
            if (password.getText().toString().trim().length() > 4 && password.getText().toString().trim().length() < 10) {
                return true;
            } else {
                Snackbar.make(findViewById(R.id.register_activity), "Password's lenght must be 5-9", Snackbar.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Snackbar.make(findViewById(R.id.register_activity), "Username lenght must be in 5-12", Snackbar.LENGTH_SHORT).show();
            return false;
        }
    }
}
