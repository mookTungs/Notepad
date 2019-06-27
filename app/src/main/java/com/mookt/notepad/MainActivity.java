package com.mookt.notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private SharedPreferences sharePref;
    private SharedPreferences.Editor edit;
    private Map<String, ?> allEntries;
    private Set<String> allKeys;
    private List<Button> listOfTitle;
    public static final String EXTRA_MESSAGE = "com.mookt.notepad.TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharePref = getSharedPreferences(getString(R.string.share_preference), MODE_PRIVATE);
        edit = sharePref.edit();
        linearLayout  = (LinearLayout) findViewById(R.id.linearLayout);
        listOfTitle = new ArrayList<Button>();
        load();
    }

    public void newButton(View view){
        Intent intent = new Intent(this, Note.class);
        startActivity(intent);
    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void load(){
        Button button;

        try {
            allEntries = sharePref.getAll();
            allKeys = allEntries.keySet();
            int i = 0;
            for(String s : allKeys){
                button = new Button(this);
                button.setText(s);
                button.setId(i);
                i++;
                button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(button);
                listOfTitle.add(button);
                final String title = button.getText().toString();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Note.class);
                        intent.putExtra(EXTRA_MESSAGE, title);
                        startActivity(intent);
                    }
                });
            }
        }catch(Exception e){
            //Toast.makeText(this,"sharePref is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void edit(View view){
        if(listOfTitle.size()!=0) {
            Intent intent = new Intent(this, Delete.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Nothing to edit", Toast.LENGTH_SHORT).show();
        }
    }
}
