package com.mookt.notepad;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Delete extends AppCompatActivity {
    private static SharedPreferences sharePref;
    private static SharedPreferences.Editor editor;
    private Map<String, ?> allEntries;
    private Set<String> allKeys;
    private LinearLayout linearLayout;
    private List<String> listOfTitleChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        sharePref = getSharedPreferences(getString(R.string.share_preference), MODE_PRIVATE);
        editor = sharePref.edit();
        listOfTitleChecked = new ArrayList<String>();
        load();
    }

    private void load(){
        try {
            allEntries = sharePref.getAll();
            allKeys = allEntries.keySet();
            linearLayout = (LinearLayout) findViewById(R.id.linearLayoutDelete);
            if(allKeys.size() == 0){
                finish();
            }

            for (String s : allKeys) {
                final CheckBox checkBox = new CheckBox(this);
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                checkBox.setText(s);
                checkBox.setTextSize(30);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            listOfTitleChecked.add(checkBox.getText().toString());
                        }else{
                            listOfTitleChecked.remove(checkBox.getText().toString());
                        }
                    }
                });

                linearLayout.addView(checkBox);
            }
        }catch(Exception e){
            finish();
        }
    }

    public void cancelBtn(View view){
        finish();
    }

    public void deleteAll(View view){
        editor.clear();
        editor.apply();
        finish();
    }

    public void deleteBtn(View view) {
        if (listOfTitleChecked.size() > 0) {
            for (String s : listOfTitleChecked) {
                editor.remove(s);
                editor.apply();
            }
            linearLayout.removeAllViews();
            load();
        }
    }
}
