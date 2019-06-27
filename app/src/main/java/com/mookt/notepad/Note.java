package com.mookt.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Note extends AppCompatActivity {
    private EditText title, textArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent = getIntent();
        String s = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        title = (EditText) findViewById(R.id.title);
        textArea = (EditText) findViewById(R.id.textArea);

        if(s != null){
            load(s);
        }
    }

    public void save(View view){
        final SharedPreferences sharedPref = getSharedPreferences(getString(R.string.share_preference), MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        final String key = title.getText().toString();
        final String value = textArea.getText().toString();

        if(key.isEmpty()){
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(sharedPref.contains(key)){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Note.this);
            alertDialog.setTitle("Overwrite");
            alertDialog.setMessage(key + " already exist. Do you want to overwrite it?");
            alertDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            editor.putString(key, value);
                            editor.apply();
                            Toast.makeText(Note.this, "Save success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
            alertDialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }else{
            editor.putString(key, value);
            editor.apply();
            Toast.makeText(this, "Save success", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void cancel(View view){
        finish();
    }

    public void load(String s){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.share_preference), MODE_PRIVATE);

        title.setText(s);
        textArea.setText(sharedPref.getString(s, ""));
    }

}
