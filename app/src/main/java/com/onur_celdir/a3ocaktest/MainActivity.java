package com.onur_celdir.a3ocaktest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.onur_celdir.a3ocaktest.audio.calculators.AudioCalculator;
import com.onur_celdir.a3ocaktest.audio.core.Callback;
import com.onur_celdir.a3ocaktest.audio.core.Recorder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "logs.txt";

    private Recorder recorder;
    private AudioCalculator audioCalculator;
    private Handler handler;

    private String currentDateTimeString;
    private String current = "non";
    private Button button;
    private Button saloon, saloon2, kitchen, corridor, bathroom, bedroom;
    private TextView textAmplitude;
    private TextView textDecibel;
    private TextView textFrequency;
    private TextView textArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.activity2button);

        // rooms
        saloon = (Button)findViewById(R.id.saloon); // 19400hz - 19600hz
        saloon2 = (Button)findViewById(R.id.saloon2); // 19400hz - 19600hz
        kitchen = (Button)findViewById(R.id.kitchen); // 19650hz - 19850hz
        bathroom = (Button)findViewById(R.id.bathroom); // 19900hz - 20100hz
        bedroom = (Button)findViewById(R.id.bedroom); // 20400hz - 20600hz
        corridor = (Button)findViewById(R.id.corridor); // 20900hz - 21100hz

        saloon.setBackgroundColor(Color.DKGRAY);
        saloon2.setBackgroundColor(Color.DKGRAY);
        kitchen.setBackgroundColor(Color.DKGRAY);
        bathroom.setBackgroundColor(Color.DKGRAY);
        bedroom.setBackgroundColor(Color.DKGRAY);
        corridor.setBackgroundColor(Color.DKGRAY);

        //button2 = (Button)findViewById((R.id.button6));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Ask for microphone permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
        recorder = new Recorder(callback);
        audioCalculator = new AudioCalculator();
        handler = new Handler(Looper.getMainLooper());
        textAmplitude = (TextView) findViewById(R.id.textAmplitude);
        // textDecibel = (TextView) findViewById(R.id.textDecibel);
        textFrequency = (TextView) findViewById(R.id.textFrequency);
        textArea = (TextView) findViewById(R.id.textArea);
    }

    private Callback callback = new Callback() {

        @Override
        public void onBufferAvailable(byte[] buffer) {
            audioCalculator.setBytes(buffer);
            int amplitude = audioCalculator.getAmplitude();
            // double decibel = audioCalculator.getDecibel();
            double frequency = audioCalculator.getFrequency();

            final String amp = String.valueOf(amplitude);
            // final String db = String.valueOf(decibel);
            final String hz = String.valueOf(frequency);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(frequency>19400 && frequency<19600 && current != "Saloon"&& amplitude > 100){
                        textArea.setText("Saloon");
                        save("Saloon");
                        current = "Saloon";
                        saloon.setBackgroundColor(Color.GREEN); saloon.setTextColor(Color.BLACK);
                        saloon2.setBackgroundColor(Color.GREEN);
                        kitchen.setBackgroundColor(Color.DKGRAY); kitchen.setTextColor(Color.WHITE);
                        bathroom.setBackgroundColor(Color.DKGRAY); bathroom.setTextColor(Color.WHITE);
                        bedroom.setBackgroundColor(Color.DKGRAY); bedroom.setTextColor(Color.WHITE);
                        corridor.setBackgroundColor(Color.DKGRAY); corridor.setTextColor(Color.WHITE);
                    }
                    else if(frequency>19650 && frequency<19850 && current != "Kitchen"&& amplitude > 100){
                        textArea.setText("Kitchen");
                        save("Kitchen");
                        current = "Kitchen";
                        saloon.setBackgroundColor(Color.DKGRAY); saloon.setTextColor(Color.WHITE);
                        saloon2.setBackgroundColor(Color.DKGRAY);
                        kitchen.setBackgroundColor(Color.GREEN); kitchen.setTextColor(Color.BLACK);
                        bathroom.setBackgroundColor(Color.DKGRAY); bathroom.setTextColor(Color.WHITE);
                        bedroom.setBackgroundColor(Color.DKGRAY); bedroom.setTextColor(Color.WHITE);
                        corridor.setBackgroundColor(Color.DKGRAY); corridor.setTextColor(Color.WHITE);
                    }
                    else if(frequency>19900 && frequency<20100 && current != "Bathroom"&& amplitude > 100){
                        textArea.setText("Bathroom");
                        save("Bathroom");
                        current = "Bathroom";
                        saloon.setBackgroundColor(Color.DKGRAY); saloon.setTextColor(Color.WHITE);
                        saloon2.setBackgroundColor(Color.DKGRAY);
                        kitchen.setBackgroundColor(Color.DKGRAY); kitchen.setTextColor(Color.WHITE);
                        bathroom.setBackgroundColor(Color.GREEN); bathroom.setTextColor(Color.BLACK);
                        bedroom.setBackgroundColor(Color.DKGRAY); bedroom.setTextColor(Color.WHITE);
                        corridor.setBackgroundColor(Color.DKGRAY); corridor.setTextColor(Color.WHITE);
                    }
                    else if(frequency>20100 && frequency<20200 && current != "Bedroom"&& amplitude > 100){
                        textArea.setText("Bedroom");
                        save("Bedroom");
                        current = "Bedroom";
                        saloon.setBackgroundColor(Color.DKGRAY); saloon.setTextColor(Color.WHITE);
                        saloon2.setBackgroundColor(Color.DKGRAY);
                        kitchen.setBackgroundColor(Color.DKGRAY); kitchen.setTextColor(Color.WHITE);
                        bathroom.setBackgroundColor(Color.DKGRAY); bathroom.setTextColor(Color.WHITE);
                        bedroom.setBackgroundColor(Color.GREEN); bedroom.setTextColor(Color.BLACK);
                        corridor.setBackgroundColor(Color.DKGRAY); corridor.setTextColor(Color.WHITE);
                    }
                    else if (frequency>19150 && frequency<19350 && current != "Corridor"&& amplitude > 100){
                        textArea.setText("Corridor");
                        save("Corridor");
                        current = "Corridor";
                        saloon.setBackgroundColor(Color.DKGRAY); saloon.setTextColor(Color.WHITE);
                        saloon2.setBackgroundColor(Color.DKGRAY);
                        kitchen.setBackgroundColor(Color.DKGRAY); kitchen.setTextColor(Color.WHITE);
                        bathroom.setBackgroundColor(Color.DKGRAY); bathroom.setTextColor(Color.WHITE);
                        bedroom.setBackgroundColor(Color.DKGRAY); bedroom.setTextColor(Color.WHITE);
                        corridor.setBackgroundColor(Color.GREEN); corridor.setTextColor(Color.BLACK);
                    }
                    textAmplitude.setText(amp);
                    // textDecibel.setText(db);
                    textFrequency.setText(hz);
                }
            });
        }
    };

    public void openActivity2(){
        Intent intent =new Intent(this, Activity2.class);
        startActivity(intent);
    }

    public void save(String position){
        FileOutputStream fos = null;
        currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

        position = "You joined to " + position + " at " + currentDateTimeString +"\n";
        byte text[] = position.getBytes();//converting string into byte array
        try {
            fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(text);
            Toast.makeText(this, "You have joined to new room!" , Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if ( fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        recorder.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        recorder.stop();
    }
}
