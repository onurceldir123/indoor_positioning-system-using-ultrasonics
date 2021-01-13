package com.onur_celdir.a3ocaktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Activity2 extends AppCompatActivity {
    private static final String FILE_NAME = "logs.txt";

    private TextView mEditText;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        mEditText = (TextView) findViewById(R.id.mEditText);
        clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFile();
            }
        });
        load();
    }


    private void load() {
        FileInputStream fis = null;

        try {

            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String text;

            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }
            if(sb.toString()=="") {
                mEditText.setText("No records found!");
            }
            else{
                mEditText.setText(sb.toString());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            if (fis != null){
                try {
                    fis.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void removeFile() {

        FileOutputStream writer = null;
        try {
            writer = openFileOutput("logs.txt", MODE_PRIVATE);
            writer.write(("").getBytes());
            writer.close();
            reload();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        Toast.makeText(this, "Successfully cleaned! " , Toast.LENGTH_LONG).show();
    }
}